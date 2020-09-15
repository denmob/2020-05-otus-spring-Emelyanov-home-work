package ru.otus.hw14.model.document;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "genres")
public class GenreDocument {
  @Id
  @BsonProperty("id")
  private String id;

  @Field(name = "name")
  private @NonNull String name;
}
