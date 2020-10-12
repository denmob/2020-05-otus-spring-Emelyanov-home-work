package ru.otus.comment.config.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.otus.library.service.RestService;
import ru.otus.library.service.RestServiceImpl;
import ru.otus.library.model.Book;
import ru.otus.library.model.Comment;


import java.util.Date;

@ChangeLog(order = "004")
public class CommentChangelog {

  private static final String URL_GET_BOOK_TITLE = "http://localhost:8001/api/book/title";

  private static final RestService<Book> BOOK_REST_SERVICE = new RestServiceImpl<>();

  @ChangeSet(order = "000", id = "dropComments", author = "dyemelianov", runAlways = true)
  public void dropComments(MongockTemplate template) {
    template.dropCollection("comments");
  }

  @ChangeSet(order = "001", id = "addComments01", author = "dyemelianov", runAlways = true)
  public void addComments01(MongockTemplate template) {

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("bookTitle", "Fundamentals");
    Book book = BOOK_REST_SERVICE.getEntity(URL_GET_BOOK_TITLE, map, Book.class);

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments01 Java Core Fundamentals")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }

  @ChangeSet(order = "002", id = "addComments02", author = "dyemelianov", runAlways = true)
  public void addComments02(MongockTemplate template) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("bookTitle", "Effective");
    Book book = BOOK_REST_SERVICE.getEntity(URL_GET_BOOK_TITLE, map, Book.class);

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments02 Effective Java")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }

  @ChangeSet(order = "003", id = "addComments03", author = "dyemelianov", runAlways = true)
  public void addComments03(MongockTemplate template) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("bookTitle", "Pragmatic");
    Book book = BOOK_REST_SERVICE.getEntity(URL_GET_BOOK_TITLE, map, Book.class);

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments03 Pragmatic Unit Testing in Java 8 with JUnit")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }
}
