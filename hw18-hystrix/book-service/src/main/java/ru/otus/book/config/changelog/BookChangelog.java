package ru.otus.book.config.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import lombok.SneakyThrows;
import ru.otus.book.service.AuthorService;
import ru.otus.book.service.GenreService;
import ru.otus.library.model.Book;

import java.text.SimpleDateFormat;
import java.util.Date;

@ChangeLog(order = "003")
public class BookChangelog {

  @ChangeSet(order = "000", id = "dropBooks", author = "dyemelianov", runAlways = true)
  public void dropBooks(MongockTemplate template) {
    template.dropCollection("books");
  }

  @ChangeSet(order = "001", id = "addBook01", author = "dyemelianov", runAlways = true)
  public void addBook01(MongockTemplate template, AuthorService authorService, GenreService genreService) {

    var author = authorService.getAuthor("Langr");
    var genre = genreService.getGenre("Programming");

    var book = Book.builder()
        .title("Java Core Fundamentals")
        .author(author)
        .genre(genre)
        .date(convertStringToDate("2016-05-17")).build();
    template.save(book);
  }

  @ChangeSet(order = "002", id = "addBook02", author = "dyemelianov", runAlways = true)
  public void addBook02(MongockTemplate template, AuthorService authorService, GenreService genreService) {

    var author = authorService.getAuthor("Bloch");
    var genre = genreService.getGenre("Science");

    var book = Book.builder()
        .title("Effective Java")
        .author(author)
        .genre(genre)
        .date(convertStringToDate("2018-01-01")).build();
    template.save(book);
  }

  @ChangeSet(order = "003", id = "addBook03", author = "dyemelianov", runAlways = true)
  public void addBook03(MongockTemplate template, AuthorService authorService, GenreService genreService) {

    var author = authorService.getAuthor("Horstmann");
    var genre = genreService.getGenre("Software");

    var book = Book.builder()
        .title("Pragmatic Unit Testing in Java 8 with JUnit")
        .author(author)
        .genre(genre)
        .date(convertStringToDate("2015-05-01")).build();
    template.save(book);
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return new SimpleDateFormat("yyyy-MM-dd").parse(date);
  }
}
