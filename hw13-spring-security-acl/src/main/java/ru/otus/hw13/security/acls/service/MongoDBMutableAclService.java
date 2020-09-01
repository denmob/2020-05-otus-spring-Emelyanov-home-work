package ru.otus.hw13.security.acls.service;

import lombok.NonNull;
import org.springframework.security.acls.domain.AccessControlEntryImpl;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.acls.model.ChildrenExistException;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.hw13.security.acls.dao.AclRepository;
import ru.otus.hw13.security.acls.domain.MongoAcl;
import ru.otus.hw13.security.acls.domain.MongoEntry;
import ru.otus.hw13.security.acls.domain.MongoSid;

import java.util.List;
import java.util.UUID;

@Service
public class MongoDBMutableAclService extends MongoDBAclService implements MutableAclService {

  public MongoDBMutableAclService(AclRepository repository, LookupStrategy lookupStrategy) {
    super(repository, lookupStrategy);
  }

  @Override
  public MutableAcl createAcl(@NonNull ObjectIdentity objectIdentity) {

    List<MongoAcl> availableAcl =
        aclRepository.findByInstanceIdAndClassName(objectIdentity.getIdentifier(), objectIdentity.getType());

    if (null != availableAcl && !availableAcl.isEmpty()) {
      throw new AlreadyExistsException("Object identity '" + objectIdentity + "' already exists");
    }

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    PrincipalSid sid = new PrincipalSid(auth);

    MongoAcl mongoAcl = MongoAcl.builder()
        .id(objectIdentity.getIdentifier())
        .className(objectIdentity.getType())
        .instanceId(UUID.randomUUID().toString())
        .owner(new MongoSid(sid.getPrincipal(), true))
        .inheritPermissions(true).build();

    aclRepository.save(mongoAcl);

    Acl acl = readAclById(objectIdentity);
    return (MutableAcl) acl;
  }

  @Override
  public void deleteAcl(@NonNull ObjectIdentity objectIdentity, boolean deleteChildren) {

    List<ObjectIdentity> children = findChildren(objectIdentity);
    if (deleteChildren) {
      if (children != null) {
        for (ObjectIdentity child : children) {
          deleteAcl(child, true);
        }
      }
    } else if (!children.isEmpty()) {
      throw new ChildrenExistException("Cannot delete '" + objectIdentity + "' (has " + children.size() + " children)");
    }
    aclRepository.deleteByInstanceId(objectIdentity.getIdentifier());
  }

  @Override
  public MutableAcl updateAcl(@NonNull MutableAcl acl) {

    MongoAcl mongoAcl = aclRepository.findById(acl.getId().toString())
        .orElseThrow(() -> new NotFoundException("No entry for ACL " + acl.getId() + " found"));

    mongoAcl.getPermissions().clear();

    for (AccessControlEntry _ace : acl.getEntries()) {
      AccessControlEntryImpl ace = (AccessControlEntryImpl) _ace;
      MongoSid sid = null;
      String aceId = (String) ace.getId();
      if (null == aceId) {
        aceId = UUID.randomUUID().toString();
      }
      if (ace.getSid() instanceof PrincipalSid) {
        PrincipalSid principal = (PrincipalSid) ace.getSid();
        sid = new MongoSid(principal.getPrincipal(), true);
      } else if (ace.getSid() instanceof GrantedAuthoritySid) {
        GrantedAuthoritySid grantedAuthority = (GrantedAuthoritySid) ace.getSid();
        sid = new MongoSid(grantedAuthority.getGrantedAuthority(), false);
      }
      MongoEntry permission = new MongoEntry(aceId, sid, ace.getPermission().getMask(), ace.isGranting(), ace.isAuditSuccess(), ace.isAuditFailure());
      mongoAcl.getPermissions().add(permission);
    }

    aclRepository.save(mongoAcl);
    return (MutableAcl) readAclById(acl.getObjectIdentity());
  }
}
