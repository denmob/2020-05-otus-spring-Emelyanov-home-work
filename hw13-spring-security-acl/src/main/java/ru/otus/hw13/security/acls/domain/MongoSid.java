package ru.otus.hw13.security.acls.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "sid")
public class MongoSid {

  private String name;

  private boolean isPrincipal;
}
