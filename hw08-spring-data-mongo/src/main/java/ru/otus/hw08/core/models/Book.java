package ru.otus.hw08.core.models;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {
  @Id
  @BsonProperty("id")
  private String id;

  @Field(name = "title")
  private String title;

  @Field(name = "date")
  private Date date;

  @Field(name = "author")
  private Author author;

  @Field(name = "genre")
  private Genre genre;

  @DBRef
  private List<Comment> comments = new ArrayList<>();
}
