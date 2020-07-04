package ru.otus.hw06.impl.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Controller;
import ru.otus.hw06.core.controller.LibraryController;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.service.ExecutorDaoService;
import ru.otus.hw06.core.service.ViewDaoService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@ShellComponent
@RequiredArgsConstructor
public class ShellLibraryController implements LibraryController {

  private final ExecutorDaoService executorDaoService;
  private final ViewDaoService viewDaoService;
  private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

  private static final String SUCCESS_OPERATION = "Success operation";
  private static final String FAILURE_OPERATION = "Failure operation";
  private static final String ILLEGAL_ARGUMENTS = "Illegal Arguments";


  @Override
  @ShellMethod(value = "Insert book command. Format input: title, data(yyyy-MM-dd), authorId, genreId", key = {"ib", "insertBook"})
  public String insertBook(@NonNull String title, @NonNull String date, long authorId, long genreId) {
    if (validateInputArgumentsForInsertBook(title, date, authorId, genreId)) {
      Book book = new Book();
      book.setTitle(title);
      book.setDate(convertStringToDate(date));
//      book.setAuthorId(authorId);
//      book.setGenreId(genreId);

      if (executorDaoService.insertBook(book)) {
        return SUCCESS_OPERATION;
      } else {
        return FAILURE_OPERATION;
      }
    } else return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Delete book command. Format input: id", key = {"db", "deleteBook"})
  public String deleteBook(long id) {
    if (executorDaoService.deleteBook(id)) {
      return SUCCESS_OPERATION;
    } else {
      return FAILURE_OPERATION;
    }
  }

  @Override
  @ShellMethod(value = "Insert author command. Format input: firstName, lastName, data(yyyy-MM-dd)", key = {"ia", "insertAuthor"})
  public String insertAuthor(@NonNull String firstName, @NonNull String lastName, @NonNull String birthday) {
    if (validateInputArgumentsForInsertAuthor(firstName, lastName, birthday)) {
      Author author = new Author();
      author.setFirstName(firstName);
      author.setLastName(lastName);
      author.setBirthday(convertStringToDate(birthday));

      if (executorDaoService.insertAuthor(author)) {
        return SUCCESS_OPERATION;
      } else {
        return FAILURE_OPERATION;
      }
    } else return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Delete author command. Format input: id", key = {"da", "deleteAuthor"})
  public String deleteAuthor(long id) {
    if (executorDaoService.deleteAuthor(id)) {
      return SUCCESS_OPERATION;
    } else {
      return FAILURE_OPERATION;
    }
  }

  @Override
  @ShellMethod(value = "Insert genre command. Format input: name ", key = {"ig", "insertGenre"})
  public String insertGenre(@NonNull String name) {
    if (validateInputArgumentsForInsertGenre(name)) {
      Genre genre = new Genre();
      genre.setName(name);

      if (executorDaoService.insertGenre(genre)) {
        return SUCCESS_OPERATION;
      } else {
        return FAILURE_OPERATION;
      }
    } else return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Delete genre command. Format input: id", key = {"dg", "deleteGenre"})
  public String deleteGenre(long id) {
    if (executorDaoService.deleteGenre(id)) {
      return SUCCESS_OPERATION;
    } else {
      return FAILURE_OPERATION;
    }
  }

  @Override
  @ShellMethod(value = "Print table books command", key = {"pb", "printBooks"})
  public void printTableBooks() {
    viewDaoService.printTableBooks();
  }

  @Override
  @ShellMethod(value = "Print table authors command", key = {"pa", "printAuthors"})
  public void printTableAuthors() {
    viewDaoService.printTableAuthors();
  }

  @Override
  @ShellMethod(value = "Print table genres command", key = {"pg", "printGenres"})
  public void printTableGenres() {
    viewDaoService.printTableGenres();
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

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return simpleDateFormat.parse(date);
  }

}
