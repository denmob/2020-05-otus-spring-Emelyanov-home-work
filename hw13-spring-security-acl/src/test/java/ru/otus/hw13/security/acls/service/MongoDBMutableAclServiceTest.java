package ru.otus.hw13.security.acls.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw13.security.acls.dao.AclRepository;
import ru.otus.hw13.security.acls.domain.MongoAcl;
import ru.otus.hw13.security.acls.domain.MongoEntry;
import ru.otus.hw13.security.acls.domain.MongoSid;
import ru.otus.hw13.security.config.AclConfig;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test")
@Import({MongoLookupStrategy.class, AclConfig.class})
@ComponentScan({"ru.otus.hw13.config", "ru.otus.hw13.security.acls.dao"})
class MongoDBMutableAclServiceTest {

  @Autowired
  private MongoDBMutableAclService mongoDBMutableAclService;

  @Autowired
  private AclRepository aclRepository;

  @AfterEach
  void cleanup() {
    aclRepository.findAll().forEach((MongoAcl acl) -> aclRepository.delete(acl));
  }

  @Test
  @WithMockUser
  @SneakyThrows
  void createAcl() {
    TestObject testObject = new TestObject();

    ObjectIdentity objectIdentity = new ObjectIdentityImpl(Class.forName(testObject.getClass().getName()), testObject.getId());
    Acl acl = mongoDBMutableAclService.createAcl(objectIdentity);

    assertThat(acl).isNotNull();
    assertThat(acl.getObjectIdentity().getIdentifier()).isEqualTo(testObject.getId());
    assertThat(acl.getObjectIdentity().getType()).isEqualTo(testObject.getClass().getName());
    assertThat(acl.getOwner()).isEqualTo(new PrincipalSid(SecurityContextHolder.getContext().getAuthentication().getName()));
  }

  @Test
  @WithMockUser
  @SneakyThrows
  void deleteAcl() {
    TestObject testObject = new TestObject();
    ObjectIdentity objectIdentity = new ObjectIdentityImpl(Class.forName(testObject.getClass().getName()), testObject.getId());

    MongoAcl mongoAcl = new MongoAcl(UUID.randomUUID().toString(), testObject.getClass().getName(), testObject.getId(),
        new MongoSid(SecurityContextHolder.getContext().getAuthentication().getName(), true));

    MongoEntry mongoEntry = new MongoEntry(UUID.randomUUID().toString(),
        new MongoSid(SecurityContextHolder.getContext().getAuthentication().getName(), true),
        BasePermission.READ.getMask() | BasePermission.WRITE.getMask(),
        true, true, true);

    mongoAcl.getPermissions().add(mongoEntry);

    aclRepository.save(mongoAcl);

    mongoDBMutableAclService.deleteAcl(objectIdentity, true);

    MongoAcl afterDelete = aclRepository.findById(mongoAcl.getId()).orElse(null);
    assertThat(afterDelete).isNull();
  }

  @Test
  @WithMockUser
  @SneakyThrows
  void updateAcl() {
    TestObject domainObject = new TestObject();
    ObjectIdentity objectIdentity = new ObjectIdentityImpl(Class.forName(domainObject.getClass().getName()), domainObject.getId());

    MongoAcl mongoAcl = new MongoAcl(UUID.randomUUID().toString(), domainObject.getClass().getName(),
        domainObject.getId(), new MongoSid(SecurityContextHolder.getContext().getAuthentication().getName(), true));

    MongoEntry mongoEntry = new MongoEntry(UUID.randomUUID().toString(),
        new MongoSid(SecurityContextHolder.getContext().getAuthentication().getName(), true),
        BasePermission.READ.getMask() | BasePermission.WRITE.getMask(),
        true, true, true);
    mongoAcl.getPermissions().add(mongoEntry);

    aclRepository.save(mongoAcl);

    MutableAcl updatedAcl = (MutableAcl) mongoDBMutableAclService.readAclById(objectIdentity);
    updatedAcl.insertAce(updatedAcl.getEntries().size(), BasePermission.ADMINISTRATION, new PrincipalSid("updateAcl"), true);

    mongoDBMutableAclService.updateAcl(updatedAcl);

    MongoAcl updated = aclRepository.findById(mongoAcl.getId()).orElse(null);
    assertThat(updated).isNotNull();
    assertThat(updated.getPermissions().size()).isEqualTo(2);
    assertThat(updated.getPermissions().get(0).getId()).isEqualTo(mongoEntry.getId());
    assertThat(updated.getPermissions().get(1).getPermission()).isEqualTo(BasePermission.ADMINISTRATION.getMask());
    assertThat(updated.getPermissions().get(1).getSid().getName()).isEqualTo("updateAcl");
    assertThat(updated.getPermissions().get(1).getSid().isPrincipal()).isTrue();
  }
}
