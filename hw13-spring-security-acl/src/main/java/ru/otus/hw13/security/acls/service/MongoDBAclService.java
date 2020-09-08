package ru.otus.hw13.security.acls.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import ru.otus.hw13.security.acls.dao.AclRepository;
import ru.otus.hw13.security.acls.domain.MongoAcl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MongoDBAclService implements AclService {

  protected final AclRepository aclRepository;

  private final LookupStrategy lookupStrategy;

  @Override
  public List<ObjectIdentity> findChildren(ObjectIdentity parentIdentity) {

    List<MongoAcl> aclsForDomainObject =
        aclRepository.findByInstanceIdAndClassName(parentIdentity.getIdentifier(), parentIdentity.getType());
    if (null == aclsForDomainObject || aclsForDomainObject.isEmpty()) {
      return new ArrayList<>();
    }

    LinkedHashSet<MongoAcl> children = new LinkedHashSet<>();
    for (MongoAcl acl : aclsForDomainObject) {
      List<MongoAcl> childAclsOfDomainObject = aclRepository.findByParentId(acl.getId());
      children.addAll(childAclsOfDomainObject);
    }

    List<ObjectIdentity> foundChildren = new ArrayList<>();
    for (MongoAcl child : children) {
      try {
        ObjectIdentity oId = new ObjectIdentityImpl(Class.forName(child.getClassName()), child.getInstanceId());
        if (!foundChildren.contains(oId)) {
          foundChildren.add(oId);
        }
      } catch (ClassNotFoundException cnfEx) {
        throw new NotFoundException(
            String.format("Could not find class of domain object %s referenced by ACL %s", child.getClassName(), child.getId()));
      }
    }
    return foundChildren;
  }

  @Override
  public Acl readAclById(ObjectIdentity object) {
    return readAclById(object, null);
  }

  @Override
  public Acl readAclById(ObjectIdentity object, List<Sid> sids) {
    Map<ObjectIdentity, Acl> map = readAclsById(Collections.singletonList(object), sids);
    return map.get(object);
  }

  @Override
  public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects) {
    return readAclsById(objects, null);
  }

  @Override
  public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects, List<Sid> sids) {
    Map<ObjectIdentity, Acl> result = lookupStrategy.readAclsById(objects, sids);

    for (ObjectIdentity oid : objects) {
      if (!result.containsKey(oid)) {
        throw new NotFoundException("Unable to find ACL information for object identity '" + oid + "'");
      }
    }
    return result;
  }
}
