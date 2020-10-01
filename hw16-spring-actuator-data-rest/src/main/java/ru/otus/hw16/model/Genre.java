package ru.otus.hw16.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Genre {
  @Id
  @BsonProperty("id")
  @JsonProperty("id")
  private String id;

  @Field(name = "name")
  @JsonProperty("name")
  private @NonNull String name;
}
