package ru.otus.hw13.security.acls.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "entry")
public class MongoEntry {

  private Serializable id;

  private MongoSid sid;

  private int permission;

  private boolean granting;

  private boolean auditFailure;

  private boolean auditSuccess;
}
