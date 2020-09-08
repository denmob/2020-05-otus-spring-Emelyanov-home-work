package ru.otus.hw13.security.acls.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MongoEntry {

  private Serializable id;

  private MongoSid sid;

  private int permission;

  private boolean granting;

  private boolean auditFailure;

  private boolean auditSuccess;
}
