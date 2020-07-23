package ru.otus.hw08.core.models;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

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

  @Field(name = "book_id")
  private String bookId;

  @Field(name = "timestamp")
  private Date timestamp;
}
