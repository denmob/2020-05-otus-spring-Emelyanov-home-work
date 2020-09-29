package ru.otus.hw16.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Document(collection = "books")
public class Book {
  @Id
  @BsonProperty("id")
  @JsonProperty("id")
  private String id;

  @Field(name = "title")
  @JsonProperty("title")
  private String title;

  @Field(name = "date")
  @JsonProperty("date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date = new Date();

  @Field(name = "author")
  @JsonProperty("author")
  private Author author;

  @Field(name = "genre")
  @JsonProperty("genre")
  private Genre genre;

}
