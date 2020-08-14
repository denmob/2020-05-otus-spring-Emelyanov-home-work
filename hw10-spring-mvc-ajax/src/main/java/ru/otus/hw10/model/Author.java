package ru.otus.hw10.model;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

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
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private @NonNull Date birthday;
}
