package ru.otus.hw17.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty("id")
  private String id;

  @Field(name = "commentary")
  @JsonProperty("commentary")
  private @NonNull String commentary;

  @Field(name = "book_id")
  @JsonProperty("bookId")
  private String bookId;

  @Field(name = "timestamp")
  @JsonProperty("timestamp")
  private Date timestamp;
}
