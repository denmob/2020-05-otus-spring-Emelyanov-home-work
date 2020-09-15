package ru.otus.hw14.model.document;

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
public class BookDocument {
  @Id
  @BsonProperty("id")
  private String id;

  @Field(name = "title")
  private String title;

  @Field(name = "date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date = new Date();

  @Field(name = "author")
  private AuthorDocument authorDocument;

  @Field(name = "genre")
  private GenreDocument genreDocument;
}
