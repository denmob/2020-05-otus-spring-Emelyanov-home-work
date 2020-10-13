package ru.otus.book.config.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import lombok.SneakyThrows;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.otus.library.service.RestService;
import ru.otus.library.service.RestServiceImpl;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;

import java.text.SimpleDateFormat;
import java.util.Date;

@ChangeLog(order = "003")
public class BookChangelog {

  private static final String URL_GET_AUTHOR_LAST_NAME = "http://author-service/api/author/lastName";
  private static final String URL_GET_GENRE_NAME = "http://genre-service/api/genre/name";

  private static final RestService<Author> AUTHOR_REST_SERVICE = new RestServiceImpl<>();
  private static final RestService<Genre> GENRE_REST_SERVICE = new RestServiceImpl<>();
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @ChangeSet(order = "000", id = "dropBooks", author = "dyemelianov", runAlways = true)
  public void dropBooks(MongockTemplate template) {
    template.dropCollection("books");
  }

  @ChangeSet(order = "001", id = "addBook01", author = "dyemelianov", runAlways = true)
  public void addBook01(MongockTemplate template) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("authorLastName", "Langr");
    Author author = AUTHOR_REST_SERVICE.getEntity(URL_GET_AUTHOR_LAST_NAME, map, Author.class);
    map.add("genreName","Programming");
    Genre genre = GENRE_REST_SERVICE.getEntity(URL_GET_GENRE_NAME, map, Genre.class);

    var book = Book.builder()
        .title("Java Core Fundamentals")
        .author(author)
        .genre(genre)
        .date(convertStringToDate("2016-05-17")).build();
    template.save(book);
  }

  @ChangeSet(order = "002", id = "addBook02", author = "dyemelianov", runAlways = true)
  public void addBook02(MongockTemplate template) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("authorLastName", "Bloch");
    Author author = AUTHOR_REST_SERVICE.getEntity(URL_GET_AUTHOR_LAST_NAME, map, Author.class);
    map.add("genreName","Science");
    Genre genre = GENRE_REST_SERVICE.getEntity(URL_GET_GENRE_NAME, map, Genre.class);

    var book = Book.builder()
        .title("Effective Java")
        .author(author)
        .genre(genre)
        .date(convertStringToDate("2018-01-01")).build();
    template.save(book);
  }

  @ChangeSet(order = "003", id = "addBook03", author = "dyemelianov", runAlways = true)
  public void addBook03(MongockTemplate template) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("authorLastName", "Horstmann");
    Author author = AUTHOR_REST_SERVICE.getEntity(URL_GET_AUTHOR_LAST_NAME, map, Author.class);
    map.add("genreName","Software");
    Genre genre = GENRE_REST_SERVICE.getEntity(URL_GET_GENRE_NAME, map, Genre.class);

    var book = Book.builder()
        .title("Pragmatic Unit Testing in Java 8 with JUnit")
        .author(author)
        .genre(genre)
        .date(convertStringToDate("2015-05-01")).build();
    template.save(book);
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return DATE_FORMAT.parse(date);
  }
}
