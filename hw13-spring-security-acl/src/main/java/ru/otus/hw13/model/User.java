package ru.otus.hw13.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

  @Id
  private String id;

  @Field(name = "username")
  private @NonNull String username;

  @Field(name = "password")
  private @NonNull String password;

  @Field(name = "role")
  private @NonNull String role;
}
