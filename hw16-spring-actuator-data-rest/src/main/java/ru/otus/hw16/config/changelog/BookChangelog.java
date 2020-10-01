package ru.otus.hw16.config.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.hw16.model.Author;
import ru.otus.hw16.model.Book;
import ru.otus.hw16.model.Genre;

import java.text.SimpleDateFormat;
import java.util.Date;

@ChangeLog(order = "003")
public class BookChangelog {

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @ChangeSet(order = "000", id = "dropBooks", author = "dyemelianov", runAlways = true)
  public void dropBooks(MongockTemplate template) {
    template.dropCollection("books");
  }

  @ChangeSet(order = "001", id = "addBook01", author = "dyemelianov", runAlways = true)
  public void addBook01(MongockTemplate template) {
    Author author = template.findOne(new Query().addCriteria(
        Criteria.where("birthday").lt(convertStringToDate("1960-11-08"))), Author.class);

    Genre genre = template.findOne(new Query().addCriteria(
        Criteria.where("name").regex("^Soft")), Genre.class);

    var book = Book.builder()
        .title("Java Core Fundamentals")
        .author(author)
        .genre(genre)
        .date(convertStringToDate("2016-05-17")).build();
    template.save(book);
  }

  @ChangeSet(order = "002", id = "addBook02", author = "dyemelianov", runAlways = true)
  public void addBook02(MongockTemplate template) {
    Author author = template.findOne(new Query().addCriteria(
        Criteria.where("birthday").lt(convertStringToDate("1969-11-08")).gt(convertStringToDate("1959-03-19"))), Author.class);

    Genre genre = template.findOne(new Query().addCriteria(
        Criteria.where("name").regex("^S")), Genre.class);

    var book = Book.builder()
        .title("Effective Java")
        .author(author)
        .genre(genre)
        .date(convertStringToDate("2018-01-01")).build();
    template.save(book);
  }

  @ChangeSet(order = "003", id = "addBook03", author = "dyemelianov", runAlways = true)
  public void addBook03(MongockTemplate template) {
    Author author = template.findOne(new Query().addCriteria(
        Criteria.where("last_name").is("Langr")), Author.class);

    Genre genre = template.findOne(new Query().addCriteria(
        Criteria.where("name").regex("^P")), Genre.class);

    var book = Book.builder()
        .title("Pragmatic Unit Testing in Java 8 with JUnit")
        .author(author)
        .genre(genre)
        .date(convertStringToDate("2015-05-01")).build();
    template.save(book);
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return dateFormat.parse(date);
  }
}
