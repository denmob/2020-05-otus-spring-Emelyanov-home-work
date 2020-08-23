package ru.otus.hw11.test.config.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw11.model.Author;

import java.text.SimpleDateFormat;
import java.util.Date;

@ChangeLog(order = "001")
public class AuthorChangelog {

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @ChangeSet(order = "000", id = "dropAuthors", author = "dyemelianov", runAlways = true)
  public void dropAuthors(MongoTemplate template) {
    template.dropCollection("authors");
  }

  @ChangeSet(order = "001", id = "addAuthor01", author = "dyemelianov", runAlways = true)
  public void insertAuthor01(MongoTemplate template) {
    var author = Author.builder().firstName("Jeff(test)").lastName("Langr").birthday(convertStringToDate("1969-11-08")).build();
    template.save(author);
  }

  @ChangeSet(order = "002", id = "addAuthor02", author = "dyemelianov", runAlways = true)
  public void insertAuthor02(MongoTemplate template) {
    var author = Author.builder().firstName("Joshua(test)").lastName("Bloch").birthday(convertStringToDate("1961-08-29")).build();
    template.save(author);
  }

  @ChangeSet(order = "003", id = "addAuthor03", author = "dyemelianov", runAlways = true)
  public void insertAuthor03(MongoTemplate template) {
    var author = Author.builder().firstName("Cay S.(test)").lastName("Horstmann").birthday(convertStringToDate("1959-03-19")).build();
    template.save(author);
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return dateFormat.parse(date);
  }
}
