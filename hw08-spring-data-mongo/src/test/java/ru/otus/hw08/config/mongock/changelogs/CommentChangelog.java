package ru.otus.hw08.config.mongock.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.hw08.core.models.Book;
import ru.otus.hw08.core.models.Comment;

import java.util.Date;

@ChangeLog(order = "004")
public class CommentChangelog {

  @ChangeSet(order = "000", id = "dropComments", author = "dyemelianov", runAlways = true)
  public void dropComments(MongoTemplate template) {
    template.dropCollection("comments");
  }

  @ChangeSet(order = "001", id = "addComments01", author = "dyemelianov", runAlways = true)
  public void addComments01(MongoTemplate template) {
    Book book = template.findOne(new Query().addCriteria(
        Criteria.where("title").is("Pragmatic Unit Testing in Java 8 with JUnit(test)")), Book.class);

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments01")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }

  @ChangeSet(order = "002", id = "addComments02", author = "dyemelianov", runAlways = true)
  public void addComments02(MongoTemplate template) {
    Book book = template.findOne(new Query().addCriteria(
        Criteria.where("title").is("Pragmatic Unit Testing in Java 8 with JUnit(test)")), Book.class);

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments02")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }

  @ChangeSet(order = "003", id = "addComments03", author = "dyemelianov", runAlways = true)
  public void addComments03(MongoTemplate template) {
    Book book = template.findOne(new Query().addCriteria(
        Criteria.where("title").is("Effective Java(test)")), Book.class);

    var comment = Comment.builder()
        .bookId(book.getId())
        .commentary("addComments03")
        .timestamp(new Date())
        .build();
    template.save(comment);
  }
}
