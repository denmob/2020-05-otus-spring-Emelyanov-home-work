package ru.otus.hw13.security.acls.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "acl_object_identity")
public class MongoAcl {

  @Id
  private Serializable id;

  private String className;

  private Serializable instanceId;

  private MongoSid owner;

  private Serializable parentId = null;

  private boolean inheritPermissions = true;

  private List<MongoEntry> permissions = new ArrayList<>();

  public MongoAcl() {
  }

  public MongoAcl(Serializable id, String className, Serializable instanceId) {
    this.id = id;
    this.instanceId = instanceId;
    this.className = className;
    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      this.owner = new MongoSid(SecurityContextHolder.getContext().getAuthentication().getName(), true);
    }
  }

  public MongoAcl(Serializable id, String className, Serializable instanceId, MongoSid owner) {
    this(id, className, instanceId);
    this.owner = owner;
  }

  public MongoAcl(Serializable id, String className, Serializable instanceId, MongoSid owner,
                  Serializable parentId, boolean entriesInheriting) {
    this(id, className, instanceId);
    this.parentId = parentId;
    this.owner = owner;
    this.inheritPermissions = entriesInheriting;
  }

  public MongoAcl(Serializable id, String className, Serializable instanceId, MongoSid owner,
                  Serializable parentId, boolean entriesInheriting, List<MongoEntry> permissions) {
    this(id, className, instanceId, owner, parentId, entriesInheriting);
    this.permissions = permissions;
  }
}
