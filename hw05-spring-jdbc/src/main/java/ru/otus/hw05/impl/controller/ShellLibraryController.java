package ru.otus.hw05.impl.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Controller;
import ru.otus.hw05.core.controller.LibraryController;
import ru.otus.hw05.core.domain.Book;
import ru.otus.hw05.core.service.ExecutorDaoService;
import ru.otus.hw05.core.service.ViewDaoService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@ShellComponent
@RequiredArgsConstructor
public class ShellLibraryController implements LibraryController {

  private final ExecutorDaoService executorDaoService;
  private final ViewDaoService viewDaoService;
  private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  @ShellMethod(value = "Insert books command. Format input data: title, data(yyyy-MM-dd), authorId, genreId.", key = {"ib", "insertBook"})
  public String insertBook(@NonNull String title, @NonNull String date, long authorId, long genreId) {
    if (validateInputArguments(title, date, authorId, genreId)) {
      Book book = new Book();
      book.setTitle(title);
      book.setDate(convertStringToDate(date));
      book.setAuthorId(authorId);
      book.setGenreId(genreId);

      if (executorDaoService.insertBook(book)) {
        return "Success insert book";
      } else {
        return "Failure insert book";
      }
    } else return "Illegal Arguments";
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

  private boolean validateInputArguments(String title, String date, long authorId, long genreId) {
    return !title.isEmpty() && convertStringToDate(date) != null && authorId > 0 && genreId > 0;
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return simpleDateFormat.parse(date);
  }

}
