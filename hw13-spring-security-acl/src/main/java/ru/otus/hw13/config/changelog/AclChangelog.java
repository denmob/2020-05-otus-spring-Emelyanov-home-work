package ru.otus.hw13.config.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import ru.otus.hw13.model.Book;
import ru.otus.hw13.model.Comment;
import ru.otus.hw13.security.acls.domain.MongoAcl;
import ru.otus.hw13.security.acls.domain.MongoEntry;
import ru.otus.hw13.security.acls.domain.MongoSid;
import ru.otus.hw13.security.config.CustomBasePermission;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ChangeLog(order = "006")
@RequiredArgsConstructor
public class AclChangelog {

  private final MongoSid alcSid = new MongoSid("test",true);
  private final MongoSid userSid = new MongoSid("ROLE_USER",false);
  private final MongoSid adminSid = new MongoSid("ROLE_ADMIN",false);

  @ChangeSet(order = "000", id = "dropAcls", author = "dyemelianov", runAlways = true)
  public void dropAcls(MongockTemplate template) {
    template.dropCollection("acl_object_identity");
  }

  @ChangeSet(order = "001", id = "addAlcComment01", author = "dyemelianov", runAlways = true)
  public void addAlcComment01(MongockTemplate template) {
    Comment comment = template.findOne(new Query().addCriteria(Criteria.where("commentary").is("addComments01")), Comment.class);

    List<MongoEntry> mongoEntryList = new ArrayList<>();
    mongoEntryList.add(createMongoEntry(userSid, BasePermission.READ));
    mongoEntryList.add(createMongoEntry(alcSid, BasePermission.ADMINISTRATION));
    mongoEntryList.add(createMongoEntry(adminSid, BasePermission.WRITE));

    template.save(createMongoAcl(comment.getClass().getName(), comment.getId(), mongoEntryList));
  }

  @ChangeSet(order = "002", id = "addAlcComment02", author = "dyemelianov", runAlways = true)
  public void addAlcComment02(MongockTemplate template) {
    Comment comment = template.findOne(new Query().addCriteria(Criteria.where("commentary").is("addComments02")), Comment.class);

    List<MongoEntry> mongoEntryList = new ArrayList<>();
    mongoEntryList.add(createMongoEntry(userSid, BasePermission.DELETE));
    mongoEntryList.add(createMongoEntry(alcSid, BasePermission.READ));
    mongoEntryList.add(createMongoEntry(adminSid, BasePermission.WRITE));

    template.save(createMongoAcl(Comment.class.getName(), comment.getId(), mongoEntryList));
  }

  @ChangeSet(order = "003", id = "addAlcComment03", author = "dyemelianov", runAlways = true)
  public void addAlcComment03(MongockTemplate template) {
    Comment comment = template.findOne(new Query().addCriteria(Criteria.where("commentary").is("addComments03")), Comment.class);

    List<MongoEntry> mongoEntryList = new ArrayList<>();
    mongoEntryList.add(createMongoEntry(userSid, BasePermission.READ));
    mongoEntryList.add(createMongoEntry(alcSid, BasePermission.READ));
    mongoEntryList.add(createMongoEntry(adminSid, BasePermission.WRITE));

    template.save(createMongoAcl(Comment.class.getName(), comment.getId(), mongoEntryList));
  }

  @ChangeSet(order = "004", id = "addAlcComment04", author = "dyemelianov", runAlways = true)
  public void addAlcComment04(MongockTemplate template) {
    Comment comment = template.findOne(new Query().addCriteria(Criteria.where("commentary").is("addComments04")), Comment.class);

    List<MongoEntry> mongoEntryList = new ArrayList<>();
    mongoEntryList.add(createMongoEntry(userSid, BasePermission.READ));
    mongoEntryList.add(createMongoEntry(alcSid, BasePermission.WRITE));
    mongoEntryList.add(createMongoEntry(adminSid, BasePermission.WRITE));

    template.save(createMongoAcl(Comment.class.getName(), comment.getId(), mongoEntryList));
  }

  @ChangeSet(order = "005", id = "addAlcBookId", author = "dyemelianov", runAlways = true)
  public void addAlcBookId(MongockTemplate template) {
    Book book = template.findOne(new Query().addCriteria(Criteria.where("title").is("Pragmatic Unit Testing in Java 8 with JUnit")), Book.class);

    List<MongoEntry> mongoEntryList = new ArrayList<>();
 //   mongoEntryList.add(createMongoEntry(userSid, BasePermission.READ ));
 //   mongoEntryList.add(createMongoEntry(userSid, BasePermission.WRITE ));
    mongoEntryList.add(createMongoEntry(userSid, CustomBasePermission.CUSTOM ));
    mongoEntryList.add(createMongoEntry(alcSid, BasePermission.READ));

    template.save(createMongoAcl(Book.class.getName(), book.getId(), mongoEntryList));
  }


  private MongoEntry createMongoEntry(MongoSid entrySid, Permission permission) {
    return new MongoEntry(UUID.randomUUID().toString(), entrySid, permission.getMask(), true, true, true);
  }

  @SneakyThrows
  private MongoAcl createMongoAcl(String className, String id, List<MongoEntry> mongoEntryList) {
    ObjectIdentity objectIdentity = new ObjectIdentityImpl(Class.forName(className), id);

    return MongoAcl.builder()
        .id(UUID.randomUUID().toString())
        .className(objectIdentity.getType())
        .instanceId(objectIdentity.getIdentifier())
        .owner(alcSid)
        .inheritPermissions(false)
        .permissions(mongoEntryList)
        .build();
  }
}
