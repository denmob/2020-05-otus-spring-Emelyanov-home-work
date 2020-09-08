package ru.otus.hw13.security.acls.service;

import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.*;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Service;
import ru.otus.hw13.security.acls.domain.MongoAcl;
import ru.otus.hw13.security.acls.domain.MongoEntry;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class MongoLookupStrategy implements LookupStrategy {

  private final MongoTemplate mongoTemplate;

  private final AclAuthorizationStrategy aclAuthorizationStrategy;

  private final PermissionGrantingStrategy grantingStrategy;

  private PermissionFactory permissionFactory = new DefaultPermissionFactory();

  private final AclCache aclCache;

  private final Field fieldAces = FieldUtils.getField(AclImpl.class, "aces");

  public MongoLookupStrategy(MongoTemplate mongoTemplate, AclAuthorizationStrategy aclAuthorizationStrategy, PermissionGrantingStrategy grantingStrategy, AclCache aclCache) {
    this.mongoTemplate = mongoTemplate;
    this.aclAuthorizationStrategy = aclAuthorizationStrategy;
    this.grantingStrategy = grantingStrategy;
    this.aclCache = aclCache;
    fieldAces.setAccessible(true);
  }

  @Override
  @SneakyThrows
  public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objectIdentities, List<Sid> sids) {
    Map<ObjectIdentity, Acl> resultMap = new HashMap<>();
    Set<ObjectIdentity> currentBatchToLoad = new HashSet<>();

    for (final ObjectIdentity objectIdentity : objectIdentities) {
      boolean aclFound = false;

      if (resultMap.containsKey(objectIdentity)) {
        aclFound = true;
      }

      if (!aclFound) {
        Acl acl = aclCache.getFromCache(objectIdentity);

        if (acl != null && acl.isSidLoaded(sids) && definesAccessPermissionsForSids(acl, sids)) {
          resultMap.put(acl.getObjectIdentity(), acl);
          aclFound = true;
        }
      }

      if (!aclFound) {
        currentBatchToLoad.add(objectIdentity);
        Map<ObjectIdentity, Acl> loadedBatch = lookupObjectIdentities(currentBatchToLoad, sids);
        resultMap.putAll(loadedBatch);
        currentBatchToLoad.clear();
      }
    }
    return resultMap;
  }

  @SneakyThrows
  private Map<ObjectIdentity, Acl> lookupObjectIdentities(final Set<ObjectIdentity> objectIdentities, List<Sid> sids) {

    List<MongoAcl> foundAcls = findMongoAncls(objectIdentities);
    Map<ObjectIdentity, Acl> resultMap = new HashMap<>();

    for (MongoAcl foundAcl : new ArrayList<>(foundAcls)) {
      Acl acl = convertToAcl(foundAcl, foundAcls);

      if (definesAccessPermissionsForSids(acl, sids)) {
        resultMap.put(acl.getObjectIdentity(), acl);
      }
    }
    return resultMap;
  }

  private List<MongoAcl> findMongoAncls(Set<ObjectIdentity> objectIdentities) {
    Set<Serializable> objectIds = new LinkedHashSet<>();
    Set<String> types = new LinkedHashSet<>();
    for (ObjectIdentity domainObject : objectIdentities) {
      objectIds.add(domainObject.getIdentifier());
      types.add(domainObject.getType());
    }
    Criteria where = Criteria.where("instanceId").in(objectIds).and("className").in(types);
    return mongoTemplate.find(query(where), MongoAcl.class);
  }

  private Acl convertToAcl(MongoAcl mongoAcl, List<MongoAcl> foundAcls) throws ClassNotFoundException {
    Acl parent = null;
    if (mongoAcl.getParentId() != null) {
      MongoAcl parentAcl = null;

      for (MongoAcl found : foundAcls) {
        if (found.getId().equals(mongoAcl.getParentId())) {
          parentAcl = found;
          break;
        }
      }

      if (null == parentAcl) {
        parentAcl = mongoTemplate.findById(mongoAcl.getParentId(), MongoAcl.class);
      }
      if (parentAcl != null) {
        if (!foundAcls.contains(parentAcl)) {
          foundAcls.add(parentAcl);
        }
        Acl cachedParent = aclCache.getFromCache(new ObjectIdentityImpl(parentAcl.getClassName(), parentAcl.getInstanceId()));
        if (null == cachedParent) {
          parent = convertToAcl(parentAcl, foundAcls);
          aclCache.putInCache((MutableAcl) parent);
        } else {
          parent = cachedParent;
        }
      }
    }

    ObjectIdentity objectIdentity = new ObjectIdentityImpl(Class.forName(mongoAcl.getClassName()), mongoAcl.getInstanceId());

    Sid owner;
    if (mongoAcl.getOwner().isPrincipal()) {
      owner = new PrincipalSid(mongoAcl.getOwner().getName());
    } else {
      owner = new GrantedAuthoritySid(mongoAcl.getOwner().getName());
    }

    AclImpl acl = new AclImpl(objectIdentity, mongoAcl.getId(), aclAuthorizationStrategy, grantingStrategy, parent,
        null, mongoAcl.isInheritPermissions(), owner);

    for (MongoEntry permission : mongoAcl.getPermissions()) {

      Sid sid;
      if (permission.getSid().isPrincipal()) {
        sid = new PrincipalSid(permission.getSid().getName());
      } else {
        sid = new GrantedAuthoritySid(permission.getSid().getName());
      }

      Permission permissions = permissionFactory.buildFromMask(permission.getPermission());
      AccessControlEntryImpl ace = new AccessControlEntryImpl(permission.getId(), acl, sid, permissions,
          permission.isGranting(), permission.isAuditSuccess(), permission.isAuditFailure());
      List<AccessControlEntryImpl> aces = readAces(acl);
      aces.add(ace);
    }

    aclCache.putInCache(acl);

    return acl;
  }

  private List<AccessControlEntryImpl> readAces(AclImpl acl) {
    try {
      @SuppressWarnings("unchecked")
      List<AccessControlEntryImpl> ret = (List<AccessControlEntryImpl>) fieldAces.get(acl);
      return ret;
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Could not obtain AclImpl.aces field", e);
    }
  }

  private boolean definesAccessPermissionsForSids(Acl acl, List<Sid> sids) {
    if (sids == null || sids.isEmpty() || sids.contains(acl.getOwner())) {
      return true;
    }
    if (hasPermissionsForSids(acl, sids)) {
      return true;
    }
    if (acl.getParentAcl() != null && acl.isEntriesInheriting()) {
      if (definesAccessPermissionsForSids(acl.getParentAcl(), sids)) {
        return true;
      }
      return hasPermissionsForSids(acl.getParentAcl(), sids);
    }
    return false;
  }

  protected boolean hasPermissionsForSids(Acl acl, List<Sid> sids) {
    for (AccessControlEntry ace : acl.getEntries()) {
      if (sids.contains(ace.getSid())) {
        return true;
      }
    }
    return false;
  }

  public final void setPermissionFactory(PermissionFactory permissionFactory) {
    this.permissionFactory = permissionFactory;
  }
}
