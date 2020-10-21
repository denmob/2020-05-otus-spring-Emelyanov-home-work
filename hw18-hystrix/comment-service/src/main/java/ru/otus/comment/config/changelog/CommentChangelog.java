package ru.otus.comment.config.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import ru.otus.comment.service.BookService;
import ru.otus.library.model.Book;
import ru.otus.library.model.Comment;

import java.util.Date;

@SuppressWarnings("unused")
@ChangeLog(order = "004")
public class CommentChangelog {

  @ChangeSet(order = "000", id = "dropComments", author = "dyemelianov", runAlways = true)
  public void dropComments(MongockTemplate template) {
    template.dropCollection("comments");
  }

  @ChangeSet(order = "001", id = "addComments01", author = "dyemelianov", runAlways = true)
  public void addComments01(MongockTemplate template, BookService bookService) {
    Book book = bookService.getBookByTitle("Java Core Fundamentals");

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments01 Java Core Fundamentals")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }

  @ChangeSet(order = "002", id = "addComments02", author = "dyemelianov", runAlways = true)
  public void addComments02(MongockTemplate template, BookService bookService) {
    Book book = bookService.getBookByTitle("Effective Java");

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments02 Effective Java")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }

  @ChangeSet(order = "003", id = "addComments03", author = "dyemelianov", runAlways = true)
  public void addComments03(MongockTemplate template, BookService bookService) {
    Book book = bookService.getBookByTitle("Pragmatic Unit Testing in Java 8 with JUnit");

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments03 Pragmatic Unit Testing in Java 8 with JUnit")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }
}
