package ru.otus.hw13.security.acls.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "acl_object_identity")
public class MongoAcl{

  @Id
  private Serializable id;

  private String className;

  private Serializable instanceId;

  private MongoSid owner;

  private Serializable parentId = null;

  private boolean inheritPermissions = true;

  private List<MongoEntry> permissions = new ArrayList<>();
}
