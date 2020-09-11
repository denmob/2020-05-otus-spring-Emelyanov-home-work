package ru.otus.hw14.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw14.service.AuthorMongoService;
import ru.otus.hw14.service.BookMongoService;
import ru.otus.hw14.service.CommentMongoService;
import ru.otus.hw14.service.GenreMongoService;

@ShellComponent
@RequiredArgsConstructor
public class MongoCommands {

  private final AuthorMongoService authorMongoService;
  private final GenreMongoService genreMongoService;
  private final BookMongoService bookMongoService;
  private final CommentMongoService commentMongoService;

  @ShellMethod(value = "view authors", key = {"vam"})
  public String viewAuthors() {
    return authorMongoService.findAll().toString();
  }

  @ShellMethod(value = "view genres", key = {"vgm"})
  public String viewGenres() {
    return genreMongoService.findAll().toString();
  }

  @ShellMethod(value = "view books", key = {"vbm"})
  public String viewBooks() {
    return bookMongoService.findAll().toString();
  }

  @ShellMethod(value = "view comments", key = {"vcm"})
  public String viewComments() {
    return commentMongoService.findAll().toString();
  }

  @ShellMethod(value = "delete comments", key = {"dcm"})
  public void deleteComments() {
    commentMongoService.deleteAll();
  }

  @ShellMethod(value = "delete authors", key = {"dam"})
  public void deleteAuthors() {
    authorMongoService.deleteAll();
  }

  @ShellMethod(value = "delete genres", key = {"dgm"})
  public void deleteGenres() {
    genreMongoService.deleteAll();
  }

  @ShellMethod(value = "delete books", key = {"dbm"})
  public void deleteBooks() {
    bookMongoService.deleteAll();
  }
}
