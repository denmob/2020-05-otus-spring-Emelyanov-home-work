package ru.otus.hw17.config.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import lombok.SneakyThrows;
import ru.otus.hw17.model.Author;

import java.text.SimpleDateFormat;
import java.util.Date;

@ChangeLog(order = "001")
public class AuthorChangelog {

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @ChangeSet(order = "000", id = "dropAuthors", author = "dyemelianov", runAlways = true)
  public void dropAuthors(MongockTemplate template) {
    template.dropCollection("authors");
  }

  @ChangeSet(order = "001", id = "addAuthor01", author = "dyemelianov", runAlways = true)
  public void insertAuthor01(MongockTemplate template) {
    var author = Author.builder().firstName("Jeff").lastName("Langr").birthday(convertStringToDate("1969-11-08")).build();
    template.save(author);
  }

  @ChangeSet(order = "002", id = "addAuthor02", author = "dyemelianov", runAlways = true)
  public void insertAuthor02(MongockTemplate template) {
    var author = Author.builder().firstName("Joshua").lastName("Bloch").birthday(convertStringToDate("1961-08-29")).build();
    template.save(author);
  }

  @ChangeSet(order = "003", id = "addAuthor03", author = "dyemelianov", runAlways = true)
  public void insertAuthor03(MongockTemplate template) {
    var author = Author.builder().firstName("Cay S.").lastName("Horstmann").birthday(convertStringToDate("1959-03-19")).build();
    template.save(author);
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return dateFormat.parse(date);
  }
}
