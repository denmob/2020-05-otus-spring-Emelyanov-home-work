package ru.otus.hw13.security.acls.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw13.security.acls.dao.AclRepository;
import ru.otus.hw13.security.acls.domain.MongoAcl;
import ru.otus.hw13.security.acls.domain.MongoEntry;
import ru.otus.hw13.security.acls.domain.MongoSid;
import ru.otus.hw13.security.config.AclConfig;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test")
@Import({MongoLookupStrategy.class, AclConfig.class})
@ComponentScan({"ru.otus.hw13.config", "ru.otus.hw13.security.acls.dao"})
class MongoDBAclServiceTest {

  @Autowired
  private MongoDBAclService mongoDBAclService;

  @Autowired
  private AclRepository aclRepository;

  private final TestObject testObject = new TestObject();
  private final TestObject firstObject = new TestObject();
  private final TestObject secondObject = new TestObject();
  private final TestObject thirdObject = new TestObject();
  private final TestObject unrelatedObject = new TestObject();

  private final MongoAcl parent = new MongoAcl(UUID.randomUUID().toString(), testObject.getClass().getName(), testObject.getId(),
      new MongoSid("owner", true));

  private final MongoAcl child1 =
      new MongoAcl(UUID.randomUUID().toString(), firstObject.getClass().getName(), firstObject.getId(),
          new MongoSid("admin", true), parent.getId(), true, new ArrayList<>());

  private final MongoAcl child2 =
      new MongoAcl(UUID.randomUUID().toString(), secondObject.getClass().getName(), secondObject.getId(),
          new MongoSid("test", true), parent.getId(), true, new ArrayList<>());

  private final MongoAcl child3 =
      new MongoAcl(UUID.randomUUID().toString(), thirdObject.getClass().getName(), thirdObject.getId(),
          new MongoSid("user", true), parent.getId(), true, new ArrayList<>());

  private final MongoAcl nonChild =
      new MongoAcl(UUID.randomUUID().toString(), unrelatedObject.getClass().getName(), unrelatedObject.getId(),
          new MongoSid("owner", true));

  @Test
  @SneakyThrows
  void findChildren() {

    aclRepository.save(parent);
    aclRepository.save(child1);
    aclRepository.save(child2);
    aclRepository.save(child3);
    aclRepository.save(nonChild);

    // Act
    ObjectIdentity parentIdentity = new ObjectIdentityImpl(Class.forName(parent.getClassName()), parent.getInstanceId());
    List<ObjectIdentity> children = mongoDBAclService.findChildren(parentIdentity);

    // Assert
    assertThat(children.size()).isEqualTo(3);

    assertThat(children.get(0).getIdentifier()).isEqualTo(firstObject.getId());
    assertThat(children.get(0).getType()).isEqualTo(firstObject.getClass().getName());

    assertThat(children.get(1).getIdentifier()).isEqualTo(secondObject.getId());
    assertThat(children.get(1).getType()).isEqualTo(secondObject.getClass().getName());
    assertThat(children.get(2).getIdentifier()).isEqualTo(thirdObject.getId());
    assertThat(children.get(2).getType()).isEqualTo(thirdObject.getClass().getName());
  }

  @Test
  @WithMockUser
  @SneakyThrows
  void testReadAclsById() {

    MongoEntry permission = new MongoEntry(UUID.randomUUID().toString(),
        new MongoSid(SecurityContextHolder.getContext().getAuthentication().getName(), true),
        BasePermission.READ.getMask() | BasePermission.WRITE.getMask(),
        true, true, true);

    parent.getPermissions().add(permission);
    child1.getPermissions().add(permission);
    child2.getPermissions().add(permission);

    aclRepository.save(parent);
    aclRepository.save(child1);
    aclRepository.save(child2);
    aclRepository.save(child3);
    aclRepository.save(nonChild);

    // Act
    List<Sid> sids = new LinkedList<>();
    sids.add(new PrincipalSid(SecurityContextHolder.getContext().getAuthentication().getName()));
    sids.add(new PrincipalSid("admin"));
    sids.add(new PrincipalSid("test"));

    ObjectIdentity parentIdentity = new ObjectIdentityImpl(Class.forName(parent.getClassName()), parent.getInstanceId());
    List<ObjectIdentity> childObjects = mongoDBAclService.findChildren(parentIdentity);
    Map<ObjectIdentity, Acl> resultUser = mongoDBAclService.readAclsById(childObjects, sids);

    assertThat(resultUser.keySet().size()).isEqualTo(3);
  }
}
