package ru.otus.hw08.impl.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Controller;
import ru.otus.hw08.core.controller.LibraryController;
import ru.otus.hw08.core.dto.BookWithComments;
import ru.otus.hw08.core.models.Book;
import ru.otus.hw08.core.models.Comment;
import ru.otus.hw08.core.service.InputReaderService;
import ru.otus.hw08.core.service.ManagerDataService;
import ru.otus.hw08.impl.service.ConsolePrintService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@ShellComponent
@RequiredArgsConstructor
public class ShellLibraryController implements LibraryController {

  private final ManagerDataService managerDataService;
  private final ConsolePrintService consolePrintService;
  private final InputReaderService inputReaderService;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
  private static final String SUCCESS_OPERATION = "Success operation";
  private static final String FAILURE_OPERATION = "Failure operation";
  private static final String ILLEGAL_ARGUMENTS = "Illegal Arguments";

  @Override
  @ShellMethod(value = "Create book command.", key = {"cb", "createBook"})
  public String createBook() {
    Book book = processingBookInteractiveStyle(new Book());
    if (managerDataService.createBook(book)) {
      return SUCCESS_OPERATION;
    }
    return FAILURE_OPERATION;
  }

  @Override
  @ShellMethod(value = "Read book command. Format input: bookTitle", key = {"rb", "readBook"})
  public String readBook(@NonNull String bookTitle) {
    if (!bookTitle.isEmpty()) {
      Optional<BookWithComments> optionalBookWithComments = managerDataService.readBookByTitle(bookTitle);
      if (optionalBookWithComments.isPresent()) {
        return optionalBookWithComments.get().toString();
      }
      return FAILURE_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Delete book command. Format input: bookTitle", key = {"db", "deleteBook"})
  public String deleteBook(@NonNull String bookTitle) {
    if (!bookTitle.isEmpty()) {
      managerDataService.deleteBookByTitle(bookTitle);
      return SUCCESS_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Create comment command. Format input: bookTitle, commentary", key = {"cc", "createComment"})
  public String createComment(@NonNull String bookTitle, @NonNull String commentary) {
    if (!bookTitle.isEmpty() && !commentary.isEmpty()) {
      if (managerDataService.createComment(bookTitle, commentary)) {
        return SUCCESS_OPERATION;
      }
      return FAILURE_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Read comment command. Format input: partComment", key = {"rc", "readComment"})
  public String readComment(@NonNull String partComment) {
    if (!partComment.isEmpty()) {
      List<Comment> comments = managerDataService.readComments(partComment);
      if (!comments.isEmpty()) {
        return StringUtils.join(comments, "|");
      }
      return FAILURE_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Delete comment command. Format input: partComment", key = {"dc", "deleteComment"})
  public String deleteComment(@NonNull String partComment) {
    if (!partComment.isEmpty()) {
      managerDataService.deleteComment(partComment);
      return SUCCESS_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Print table books command", key = {"pb", "printBooks"})
  public void printTableBooks() {
    managerDataService.printTableBooks();
  }

  @Override
  @ShellMethod(value = "Print table authors command", key = {"pa", "printAuthors"})
  public void printTableAuthors() {
    managerDataService.printTableAuthors();
  }

  @Override
  @ShellMethod(value = "Print table genres command", key = {"pg", "printGenres"})
  public void printTableGenres() {
    managerDataService.printTableGenres();
  }

  @Override
  @ShellMethod(value = "Print table comments command", key = {"pc", "printComments"})
  public void printTableComments() {
    managerDataService.printTableComments();
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return dateFormat.parse(date);
  }

  private Book processingBookInteractiveStyle(Book book) {
    int actualTryInput = 0;
    int tryInput = 3;
    do {
      try {
        if (book.getTitle() == null) {
          consolePrintService.printlnMessage("Please input title book");
          String data = inputReaderService.readLine();
          if (!data.isEmpty()) {
            book.setTitle(data);
          }
        }
        if (book.getDate() == null) {
          consolePrintService.printlnMessage("Please input date of issue book. format: " + dateFormat.toPattern());
          String date = inputReaderService.readLine();
          book.setDate(convertStringToDate(date));
        }
        if (book.getGenre() == null) {
          consolePrintService.printlnMessage("Please input name exist genre of book.");
          String date = inputReaderService.readToken();
          managerDataService.readGenreByName(date).ifPresent(book::setGenre);
        }
        if (book.getAuthor() == null) {
          consolePrintService.printlnMessage("Please input lastName exist author of book.");
          String date = inputReaderService.readToken();
          managerDataService.readAuthorByLastName(date).ifPresent(book::setAuthor);
        }
      } catch (Exception e) {
        consolePrintService.printlnMessage(e.getMessage());
        ++actualTryInput;
      }
    } while (!isValidBook(book) && actualTryInput < tryInput - 1);
    return book;
  }

  private boolean isValidBook(Book book) {
    return (book.getAuthor() != null && book.getGenre() != null && !book.getTitle().isEmpty() && book.getDate() != null);
  }
}
