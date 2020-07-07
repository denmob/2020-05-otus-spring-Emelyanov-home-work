package ru.otus.hw06.impl.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Controller;
import ru.otus.hw06.core.controller.LibraryController;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Comment;
import ru.otus.hw06.core.service.ViewRepositoryService;
import ru.otus.hw06.impl.service.CRUDAuthorService;
import ru.otus.hw06.impl.service.CRUDBookService;
import ru.otus.hw06.impl.service.CRUDCommentService;
import ru.otus.hw06.impl.service.CRUDGenreService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@ShellComponent
@RequiredArgsConstructor
public class ShellLibraryController implements LibraryController {

  private final CRUDBookService crudBookService;
  private final CRUDCommentService crudCommentService;
  private final CRUDAuthorService crudAuthorService;
  private final CRUDGenreService crudGenreService;

  private final ViewRepositoryService viewRepositoryService;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private static final String SUCCESS_OPERATION = "Success operation";
  private static final String FAILURE_OPERATION = "Failure operation";
  private static final String ILLEGAL_ARGUMENTS = "Illegal Arguments";

  @Override
  @ShellMethod(value = "Create book command. Format input: title, data(yyyy-MM-dd), authorId, genreId", key = {"cb", "createBook"})
  public String createBook(@NonNull String title, @NonNull String date, long authorId, long genreId) {
    if (!title.isEmpty() && convertStringToDate(date) != null && authorId > 0 && genreId > 0) {
      Book.BookBuilder builder = Book.builder();
      builder.title(title);
      builder.date(convertStringToDate(date));
      crudAuthorService.read(authorId).ifPresent(builder::author);
      crudGenreService.read(genreId).ifPresent(builder::genre);
      Book book = builder.build();
      if (book.getAuthor() != null && book.getGenre() != null && crudBookService.create(book) != null) {
        return SUCCESS_OPERATION;
      }
      return FAILURE_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Read book command. Format input: bookId", key = {"rb", "readBook"})
  public String readBook(long bookId) {
    if (bookId > 0) {
      Optional<Book> book = crudBookService.read(bookId);
      if (book.isPresent()) {
        return book.get().toString();
      }
      return FAILURE_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Update book command. Format input: bookId, title, data(yyyy-MM-dd), authorId, genreId", key = {"ub", "updateBook"})
  public String updateBook(long bookId, String title, String date, long authorId, long genreId) {
    if (bookId > 0) {
      Optional<Book> optionalBook = crudBookService.read(bookId);
      if (optionalBook.isPresent()) {
        Book book = optionalBook.get();
        book.setTitle(title == null ? book.getTitle() : title);
        book.setDate(date == null ? book.getDate() : convertStringToDate(date));
        if (authorId > 0) {
          crudAuthorService.read(authorId).ifPresent(book::setAuthor);
        }
        if (genreId > 0) {
          crudGenreService.read(genreId).ifPresent(book::setGenre);
        }
        if (crudBookService.update(book) != null) {
          return SUCCESS_OPERATION;
        }
      }
      return FAILURE_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Delete book command. Format input: bookId", key = {"db", "deleteBook"})
  public String deleteBook(long bookId) {
    if (bookId > 0) {
      if (crudBookService.delete(bookId)) {
        return SUCCESS_OPERATION;
      }
      return FAILURE_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Create comment command. Format input: bookId, commentary", key = {"cc", "createComment"})
  public String createComment(long bookId, @NonNull String commentary) {
    if (bookId > 0 && !commentary.isEmpty()) {
      Comment comment = new Comment(0L, commentary);

      Optional<Book> optionalBook = crudBookService.read(bookId);
      if (optionalBook.isPresent()) {
        Book book = optionalBook.get();
        book.getComments().add(comment);

        if (crudCommentService.create(comment) != null && crudBookService.update(book) != null) {
          return SUCCESS_OPERATION;
        }
      }
      return FAILURE_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
  }

  @Override
  public String readComment(long commentId) {
    if (commentId > 0) {
      Optional<Comment> comment = crudCommentService.read(commentId);
      if (comment.isPresent()) {
        return comment.get().toString();
      }
      return FAILURE_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Update comment command. Format input: commentId, commentary", key = {"uc", "updateComment"})
  public String updateComment(long commentId, String commentary) {
    if (commentId > 0) {
      Optional<Comment> commentOptional = crudCommentService.read(commentId);
      if (commentOptional.isPresent()) {
        Comment comment = commentOptional.get();
        comment.setCommentary(commentary);
        if (crudCommentService.update(comment) != null) {
          return SUCCESS_OPERATION;
        }
      }
      return FAILURE_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
  }

  @Override
  @ShellMethod(value = "Delete comment command. Format input: commentId", key = {"dc", "deleteComment"})
  public String deleteComment(long commentId) {
    if (commentId > 0) {
      if (crudCommentService.delete(commentId)) {
        return SUCCESS_OPERATION;
      }
      return FAILURE_OPERATION;
    }
    return ILLEGAL_ARGUMENTS;
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

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return dateFormat.parse(date);
  }

}
