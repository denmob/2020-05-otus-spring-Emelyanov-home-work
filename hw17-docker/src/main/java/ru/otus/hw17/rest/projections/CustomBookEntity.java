package ru.otus.hw17.rest.projections;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.otus.hw17.model.Author;
import ru.otus.hw17.model.Book;
import ru.otus.hw17.model.Genre;

import javax.naming.OperationNotSupportedException;
import java.util.Date;
import java.util.Map;

@Slf4j
public class CustomBookEntity implements CustomBook {

  private final Book book;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public CustomBookEntity(@JsonProperty("title") String title,
                          @JsonProperty("date") Date date,
                          @JsonProperty("author") Author author,
                          @JsonProperty("genre") Genre genre
  ) {
    book = Book.builder().title(title).date(date).author(author).genre(genre).build();
  }

  @Override
  public String getTitle() {
    return book.getTitle();
  }

  @Override
  public Date getDate() {
    return book.getDate();
  }

  @Override
  public Author getAuthor() {
    return book.getAuthor();
  }

  @Override
  public Genre getGenre() {
    return book.getGenre();
  }

  @Override
  @SneakyThrows
  public Map<String, Object> getLinksMap() {
    throw new OperationNotSupportedException("Links not implemented");
  }
}
