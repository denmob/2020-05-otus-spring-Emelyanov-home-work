package ru.otus.hw06.impl.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Comment;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.service.ViewRepositoryService;
import ru.otus.hw06.impl.service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ShellLibraryController.class, ViewRepositoryServiceImpl.class, CRUDGenreService.class, CRUDCommentService.class, CRUDBookService.class, CRUDAuthorService.class})
class ShellLibraryControllerTest {

  @MockBean
  private CRUDBookService crudBookService;
  @MockBean
  private CRUDCommentService crudCommentService;
  @MockBean
  private CRUDAuthorService crudAuthorService;
  @MockBean
  private CRUDGenreService crudGenreService;

  @MockBean
  private ViewRepositoryService viewRepositoryService;

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  private static final String SUCCESS_OPERATION = "Success operation";
  private static final String FAILURE_OPERATION = "Failure operation";
  private static final String ILLEGAL_ARGUMENTS = "Illegal Arguments";

  @Autowired
  private ShellLibraryController shellLibraryController;

  @Test
  void createBookSUCCESS_OPERATION() {
    Author author = new Author(1L, "FirstName", "LastName", now());
    Genre genre = new Genre(1L, "newGenre");
    Book book = new Book(0L, "Title", convertStringToDate("2020-01-01"), author, genre, null);

    when(crudAuthorService.read(author.getId())).thenReturn(Optional.of(author));
    when(crudGenreService.read(genre.getId())).thenReturn(Optional.of(genre));
    when(crudBookService.create(book)).thenReturn(book);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.createBook(book.getTitle(), convertDateToString(book.getDate()), author.getId(), genre.getId()));
  }

  @Test
  void createBookFAILURE_OPERATION() {
    Author author = new Author(1L, "FirstName", "LastName", now());
    Genre genre = new Genre(1L, "newGenre");
    Book book = new Book(0L, "Title", convertStringToDate("2020-01-01"), author, genre, null);

    when(crudAuthorService.read(author.getId())).thenReturn(Optional.empty());
    when(crudGenreService.read(genre.getId())).thenReturn(Optional.empty());
    when(crudBookService.create(book)).thenReturn(book);

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.createBook(book.getTitle(), convertDateToString(book.getDate()), author.getId(), genre.getId()));
  }

  @Test
  void createBookILLEGAL_ARGUMENTS() {
    Author author = new Author(0L, "FirstName", "LastName", now());
    Genre genre = new Genre(0L, "newGenre");
    Book book = new Book(0L, "Title", convertStringToDate("2020-01-01"), author, genre, null);

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.createBook(book.getTitle(), convertDateToString(book.getDate()), author.getId(), genre.getId()));
  }

  @Test
  void createBookThrowParseDateToString() {
    assertThrows(ParseException.class, () -> {
      shellLibraryController.createBook("Test", "20200101", 1, 1);
    });
  }

  @Test
  void readBookSUCCESS_OPERATION() {
    Book book = new Book(1L, "Title", convertStringToDate("2020-01-01"), null, null, null);
    when(crudBookService.read(book.getId())).thenReturn(Optional.of(book));

    Assertions.assertEquals(book.toString(), shellLibraryController.readBook(book.getId()));
  }

  @Test
  void readBookFAILURE_OPERATION() {
    Book book = new Book(1L, "Title", convertStringToDate("2020-01-01"), null, null, null);
    when(crudBookService.read(book.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.readBook(book.getId()));
  }

  @Test
  void readBookILLEGAL_ARGUMENTS() {
    Book book = new Book(0L, "Title", convertStringToDate("2020-01-01"), null, null, null);

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.readBook(book.getId()));
  }

  @Test
  void updateBookSUCCESS_OPERATION() {
    Author author = new Author(0L, "FirstName", "LastName", now());
    Genre genre = new Genre(0L, "newGenre");
    Book book = new Book(1L, "Title", convertStringToDate("2020-01-01"), author, genre, null);

    when(crudBookService.read(book.getId())).thenReturn(Optional.of(book));
    when(crudAuthorService.read(author.getId())).thenReturn(Optional.of(author));
    when(crudGenreService.read(genre.getId())).thenReturn(Optional.of(genre));
    when(crudBookService.update(book)).thenReturn(book);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.updateBook(book.getId(), book.getTitle(), convertDateToString(book.getDate()), author.getId(), genre.getId()));
  }

  @Test
  void updateBookFAILURE_OPERATION() {
    Author author = new Author(0L, "FirstName", "LastName", now());
    Genre genre = new Genre(0L, "newGenre");
    Book book = new Book(1L, "Title", convertStringToDate("2020-01-01"), author, genre, null);

    when(crudBookService.read(book.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.updateBook(book.getId(), book.getTitle(), convertDateToString(book.getDate()), author.getId(), genre.getId()));
  }

  @Test
  void updateBookILLEGAL_ARGUMENTS() {
    Author author = new Author(0L, "FirstName", "LastName", now());
    Genre genre = new Genre(0L, "newGenre");
    Book book = new Book(0L, "Title", convertStringToDate("2020-01-01"), author, genre, null);

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.updateBook(book.getId(), book.getTitle(), convertDateToString(book.getDate()), author.getId(), genre.getId()));
  }


  @Test
  void deleteBookSUCCESS_OPERATION() {
    long bookId = 1L;
    when(crudBookService.delete(bookId)).thenReturn(true);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.deleteBook(bookId));
  }

  @Test
  void deleteBookFAILURE_OPERATION() {
    long bookId = 1L;
    when(crudBookService.delete(bookId)).thenReturn(false);

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.deleteBook(bookId));
  }

  @Test
  void deleteBookILLEGAL_ARGUMENTS() {
    long bookId = 0L;

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.deleteBook(bookId));
  }


  @Test
  void createCommentSUCCESS_OPERATION() {
    long bookId = 1L;
    Book book = new Book();
    book.setComments(new ArrayList<>());
    Comment comment = new Comment(0L, "comment");

    when(crudBookService.read(bookId)).thenReturn(java.util.Optional.of(book));
    when(crudCommentService.create(comment)).thenReturn(comment);
    when(crudBookService.update(book)).thenReturn(book);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.createComment(bookId, comment.getCommentary()));
  }

  @Test
  void createCommentILLEGAL_ARGUMENTS() {
    long bookId = 0L;
    when(crudBookService.read(bookId)).thenReturn(java.util.Optional.empty());

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.createComment(bookId, ""));
  }

  @Test
  void createCommentFAILURE_OPERATION() {
    long bookId = 10L;
    when(crudBookService.read(bookId)).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.createComment(bookId, "comment"));
  }

  @Test
  void readCommentSUCCESS_OPERATION() {
    Comment comment = new Comment(1L, "comment");
    when(crudCommentService.read(comment.getId())).thenReturn(java.util.Optional.of(comment));

    Assertions.assertEquals(comment.toString(), shellLibraryController.readComment(comment.getId()));
  }

  @Test
  void readCommentFAILURE_OPERATION() {
    Comment comment = new Comment(1L, "comment");
    when(crudCommentService.read(comment.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.readComment(comment.getId()));
  }

  @Test
  void readCommentILLEGAL_ARGUMENTS() {
    Comment comment = new Comment(0L, "comment");
    when(crudCommentService.read(comment.getId())).thenReturn(Optional.of(comment));

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.readComment(comment.getId()));
  }

  @Test
  void updateCommentSUCCESS_OPERATION() {
    Comment comment = new Comment(1L, "comment");
    when(crudCommentService.read(comment.getId())).thenReturn(Optional.of(comment));
    when(crudCommentService.update(comment)).thenReturn(comment);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.updateComment(comment.getId(), comment.getCommentary()));
  }

  @Test
  void updateCommentFAILURE_OPERATION() {
    Comment comment = new Comment(1L, "comment");
    when(crudCommentService.read(comment.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.updateComment(comment.getId(), comment.getCommentary()));
  }

  @Test
  void updateCommentILLEGAL_ARGUMENTS() {
    Comment comment = new Comment(0L, "comment");
    when(crudCommentService.read(comment.getId())).thenReturn(Optional.of(comment));

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.updateComment(comment.getId(), comment.getCommentary()));
  }

  @Test
  void deleteCommentSUCCESS_OPERATION() {
    long commentId = 1L;
    when(crudCommentService.delete(commentId)).thenReturn(true);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.deleteComment(commentId));
  }

  @Test
  void deleteCommentFAILURE_OPERATION() {
    long commentId = 1L;
    when(crudCommentService.delete(commentId)).thenReturn(false);

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.deleteComment(commentId));
  }

  @Test
  void deleteCommentILLEGAL_ARGUMENTS() {
    long commentId = 0L;

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.deleteComment(commentId));
  }

  @Test
  void printTableBooks() {
    Mockito.doNothing().when(viewRepositoryService).printTableBooks();

    shellLibraryController.printTableBooks();

    Mockito.verify(viewRepositoryService, Mockito.times(1)).printTableBooks();
  }

  @Test
  void printTableAuthors() {
    Mockito.doNothing().when(viewRepositoryService).printTableAuthors();

    shellLibraryController.printTableAuthors();

    Mockito.verify(viewRepositoryService, Mockito.times(1)).printTableAuthors();
  }

  @Test
  void printTableGenres() {
    Mockito.doNothing().when(viewRepositoryService).printTableGenres();

    shellLibraryController.printTableGenres();

    Mockito.verify(viewRepositoryService, Mockito.times(1)).printTableGenres();
  }

  @Test
  void printTableComments() {
    Mockito.doNothing().when(viewRepositoryService).printTableComments();

    shellLibraryController.printTableComments();

    Mockito.verify(viewRepositoryService, Mockito.times(1)).printTableComments();
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return DATE_FORMAT.parse(date);
  }

  @SneakyThrows
  private String convertDateToString(Date date) {
    return DATE_FORMAT.format(date);
  }
}
