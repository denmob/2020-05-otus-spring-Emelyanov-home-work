package ru.otus.hw09.model;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authors")
public class Author {
  @Id
  @BsonProperty("id")
  private String id;

  @Field(name = "first_name")
  private @NonNull String firstName;

  @Field(name = "last_name")
  private @NonNull String lastName;

  @Field(name = "birthday")
  private @NonNull Date birthday;
}
