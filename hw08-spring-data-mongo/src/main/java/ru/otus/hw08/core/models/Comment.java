package ru.otus.hw08.core.models;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {
  @Id
  @BsonProperty("id")
  private String id;

  @Field(name = "commentary")
  private @NonNull String commentary;

  @DBRef
  private Book book;
}
