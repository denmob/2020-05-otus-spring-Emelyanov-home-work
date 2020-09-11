package ru.otus.hw14.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.hw14.model.document.Book;
import ru.otus.hw14.model.document.Comment;

import java.util.Date;

@ChangeLog(order = "004")
public class CommentChangelog {

  @ChangeSet(order = "000", id = "dropComments", author = "dyemelianov", runAlways = true)
  public void dropComments(MongockTemplate template) {
    template.dropCollection("comments");
  }

  @ChangeSet(order = "001", id = "addComments01", author = "dyemelianov", runAlways = true)
  public void addComments01(MongockTemplate template) {
    Book book = template.findOne(new Query().addCriteria(
        Criteria.where("title").is("Pragmatic Unit Testing in Java 8 with JUnit")), Book.class);

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments01")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }

  @ChangeSet(order = "002", id = "addComments02", author = "dyemelianov", runAlways = true)
  public void addComments02(MongockTemplate template) {
    Book book = template.findOne(new Query().addCriteria(
        Criteria.where("title").is("Pragmatic Unit Testing in Java 8 with JUnit")), Book.class);

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments02")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }

  @ChangeSet(order = "003", id = "addComments03", author = "dyemelianov", runAlways = true)
  public void addComments03(MongockTemplate template) {
    Book book = template.findOne(new Query().addCriteria(
        Criteria.where("title").is("Effective Java")), Book.class);

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments03")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }

  @ChangeSet(order = "004", id = "addComments04", author = "dyemelianov", runAlways = true)
  public void addComments04(MongockTemplate template) {
    Book book = template.findOne(new Query().addCriteria(
        Criteria.where("title").is("Pragmatic Unit Testing in Java 8 with JUnit")), Book.class);

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments04")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }
}
