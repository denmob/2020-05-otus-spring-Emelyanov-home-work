package ru.otus.hw06.impl.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Controller;
import ru.otus.hw06.core.controller.LibraryController;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.service.ViewRepositoryService;
import ru.otus.hw06.impl.service.CRUDBookService;
import ru.otus.hw06.impl.service.CRUDCommentService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@ShellComponent
@RequiredArgsConstructor
public class ShellLibraryController implements LibraryController {

  private final CRUDBookService crudBookService;
  private final CRUDCommentService crudCommentService;
  private final ViewRepositoryService viewRepositoryService;
  private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

  private static final String SUCCESS_OPERATION = "Success operation";
  private static final String FAILURE_OPERATION = "Failure operation";
  private static final String ILLEGAL_ARGUMENTS = "Illegal Arguments";


  @Override
  @ShellMethod(value = "Create book command. Format input: title, data(yyyy-MM-dd), authorId, genreId", key = {"cb", "createBook"})
  public String createBook(@NonNull String title, @NonNull String date, long authorId, long genreId) {
   return SUCCESS_OPERATION;
  }

  @Override
  @ShellMethod(value = "Read book command. Format input: id", key = {"rb", "readBook"})
  public String readBook(long id) {
    if (validateArgumentId(id)) {
      Optional<Book> book = crudBookService.read(id);
      if (book.isPresent()) {
        return book.get().toString();
      }
      return FAILURE_OPERATION;
    } return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Update book command. Format input: id, title, data(yyyy-MM-dd), authorId, genreId", key = {"ub", "updateBook"})
  public String updateBook(long id, String title, String date, long authorId, long genreId) {
    return SUCCESS_OPERATION;
  }

  @Override
  @ShellMethod(value = "Delete book command. Format input: id", key = {"db", "deleteBook"})
  public String deleteBook(long id) {
    return SUCCESS_OPERATION;
  }

  @Override
  public String createComment(long bookId, String commentary) {
    return null;
  }

  @Override
  public String readComment(long id) {
    return null;
  }

  @Override
  public String updateComment(long id, String commentary) {
    return null;
  }

  @Override
  public String deleteComment(long id) {
    return null;
  }

  @Override
  @ShellMethod(value = "Print table books command", key = {"pb", "printBooks"})
  public void printTableBooks() {
    viewRepositoryService.printTableBooks();
  }

  @Override
  @ShellMethod(value = "Print table authors command", key = {"pa", "printAuthors"})
  public void printTableAuthors() {
    viewRepositoryService.printTableAuthors();
  }

  @Override
  @ShellMethod(value = "Print table genres command", key = {"pg", "printGenres"})
  public void printTableGenres() {
    viewRepositoryService.printTableGenres();
  }

  @Override
  @ShellMethod(value = "Print table comments command", key = {"pc", "printComments"})
  public void printTableComments() {
    viewRepositoryService.printTableComments();
  }

  private boolean validateInputArgumentsForInsertBook(String title, String date, long authorId, long genreId) {
    return !title.isEmpty() && convertStringToDate(date) != null && authorId > 0 && genreId > 0;
  }

  private boolean validateInputArgumentsForInsertAuthor(String firstName, String lastName, String birthday) {
    return !firstName.isEmpty() && !lastName.isEmpty() && convertStringToDate(birthday) != null;
  }

  private boolean validateInputArgumentsForInsertGenre(String name) {
    return !name.isEmpty();
  }

  private boolean validateArgumentId(long id){
    return id>0;
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return simpleDateFormat.parse(date);
  }

}
