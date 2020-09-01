package ru.otus.hw13.security.acls.service;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import ru.otus.hw13.security.acls.domain.MongoAcl;
import ru.otus.hw13.security.acls.domain.MongoEntry;

import java.io.Serializable;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
public class MongoLookupStrategy implements LookupStrategy {

  @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
  @Autowired
  private MongoTemplate mongoTemplate;

  private final AclAuthorizationStrategy aclAuthorizationStrategy;

  private final PermissionGrantingStrategy grantingStrategy;

  private final PermissionFactory permissionFactory = new DefaultPermissionFactory();

  @Override
  @SneakyThrows
  public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objectIdentities, List<Sid> sids) {
    Map<ObjectIdentity, Acl> resultMap = new HashMap<>();

    List<MongoAcl> mongoAcls = findMongoAncls(objectIdentities);
    for (final MongoAcl mongoAcl : mongoAcls) {

      ObjectIdentity objectIdentity = new ObjectIdentityImpl(Class.forName(mongoAcl.getClassName()), mongoAcl.getInstanceId());
      Sid owner;
      if (mongoAcl.getOwner().isPrincipal()) {
        owner = new PrincipalSid(mongoAcl.getOwner().getName());
      } else {
        owner = new GrantedAuthoritySid(mongoAcl.getOwner().getName());
      }
      AclImpl acl = new AclImpl(objectIdentity, mongoAcl.getId(), aclAuthorizationStrategy, grantingStrategy, null,
          null, mongoAcl.isInheritPermissions(), owner);

      for (MongoEntry permission : mongoAcl.getPermissions()) {
        Sid sid;
        if (permission.getSid().isPrincipal()) {
          sid = new PrincipalSid(permission.getSid().getName());
        } else {
          sid = new GrantedAuthoritySid(permission.getSid().getName());
        }
        Permission permissions = permissionFactory.buildFromMask(permission.getPermission());
        AccessControlEntryImpl ace =
            new AccessControlEntryImpl(permission.getId(), acl, sid, permissions,
                permission.isGranting(), permission.isAuditSuccess(), permission.isAuditFailure());

        acl.getEntries().add(ace);
      }
      if (definesAccessPermissionsForSids(acl, sids)) {
        resultMap.put(acl.getObjectIdentity(), acl);
      }
    }

    return resultMap;
  }

  private List<MongoAcl> findMongoAncls(List<ObjectIdentity> objectIdentities) {
    Set<Serializable> objectIds = new LinkedHashSet<>();
    Set<String> types = new LinkedHashSet<>();
    for (ObjectIdentity domainObject : objectIdentities) {
      objectIds.add(domainObject.getIdentifier());
      types.add(domainObject.getType());
    }
    Criteria where = Criteria.where("instanceId").in(objectIds).and("className").in(types);
    return mongoTemplate.find(query(where), MongoAcl.class);
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
}
