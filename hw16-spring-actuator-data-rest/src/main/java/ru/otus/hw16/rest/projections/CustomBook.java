package ru.otus.hw16.rest.projections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import ru.otus.hw16.model.Author;
import ru.otus.hw16.model.Book;
import ru.otus.hw16.model.Genre;

import java.util.Date;
import java.util.Map;

@Projection(name = "customBook", types = {Book.class, Author.class, Genre.class})
public interface CustomBook {

  @Value("#{target.title}")
  @JsonProperty("title")
  String getTitle();

  @Value("#{target.date}")
  @JsonProperty("date")
  Date getDate();

  @Value("#{target.author}")
  @JsonProperty("author")
  Author getAuthor();

  @Value("#{target.genre}")
  @JsonProperty("genre")
  Genre getGenre();

  @JsonIgnore
  @JsonProperty("_links")
  @Value("#{target._links}")
  Map<String, Object> getLinksMap();
}
