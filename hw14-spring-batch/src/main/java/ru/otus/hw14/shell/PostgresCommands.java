package ru.otus.hw14.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw14.service.*;

@ShellComponent
@RequiredArgsConstructor
public class PostgresCommands {

  private final AuthorCrudService authorCrudService;
  private final GenreCrudService genreCrudService;
  private final BookCrudService bookCrudService;
  private final CommentCrudService commentCrudService;

  @ShellMethod(value = "view authors ", key = {"vap"})
  public String viewAuthors() {
    return authorCrudService.findAll().toString();
  }

  @ShellMethod(value = "view genres", key = {"vgp"})
  public String viewGenres() {
    return genreCrudService.findAll().toString();
  }

  @ShellMethod(value = "view books", key = {"vbp"})
  public String viewBooks() {
    return bookCrudService.findAll().toString();
  }

  @ShellMethod(value = "view comments", key = {"vcp"})
  public String viewComments() {
    return commentCrudService.findAll().toString();
  }

  @ShellMethod(value = "delete authors", key = {"dap"})
  public void deleteAuthors() {
    authorCrudService.deleteAll();
  }

  @ShellMethod(value = "delete genres", key = {"dgp"})
  public void deleteGenres() {
    genreCrudService.deleteAll();
  }

  @ShellMethod(value = "delete books", key = {"dbp"})
  public void deleteBooks() {
    bookCrudService.deleteAll();
  }

  @ShellMethod(value = "delete comments", key = {"dcp"})
  public void deleteComments() {
    commentCrudService.deleteAll();
  }
}
