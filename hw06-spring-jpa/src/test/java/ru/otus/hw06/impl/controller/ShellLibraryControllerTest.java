package ru.otus.hw06.impl.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw06.core.dto.BookWithComments;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Comment;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.service.ViewRepositoryService;
import ru.otus.hw06.impl.service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
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
  private InputReaderServiceImpl inputReaderService;
  @MockBean
  private ConsolePrintService consolePrintService;

  @MockBean
  private ViewRepositoryService viewRepositoryService;

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy MM dd");
  private static final String SUCCESS_OPERATION = "Success operation";
  private static final String FAILURE_OPERATION = "Failure operation";
  private static final String ILLEGAL_ARGUMENTS = "Illegal Arguments";

  @Autowired
  private ShellLibraryController shellLibraryController;

  @Test
  void readBookSUCCESS_OPERATION() {
    Book book = new Book(1L, "Title", convertStringToDate("2020 01 01"), null, null);
    BookWithComments bookWithComments = new BookWithComments(book,null);
    when(crudBookService.readWithComments(book.getId())).thenReturn(Optional.of(bookWithComments));

    Assertions.assertEquals(bookWithComments.toString(), shellLibraryController.readBook(book.getId()));
  }

  @Test
  void readBookFAILURE_OPERATION() {
    Book book = new Book(1L, "Title", convertStringToDate("2020 01 01"), null, null);
    when(crudBookService.read(book.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.readBook(book.getId()));
  }

  @Test
  void readBookILLEGAL_ARGUMENTS() {
    Book book = new Book(0L, "Title", convertStringToDate("2020 01 01"), null, null);

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.readBook(book.getId()));
  }

  @Test
  void deleteBookSUCCESS_OPERATION() {
    long bookId = 1L;
    when(crudBookService.delete(bookId)).thenReturn(true);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.deleteBook(bookId));
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
    Comment comment = new Comment(0L, "comment",new Book());

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
    Comment comment = new Comment(1L, "comment",new Book());
    when(crudCommentService.read(comment.getId())).thenReturn(java.util.Optional.of(comment));

    Assertions.assertEquals(comment.toString(), shellLibraryController.readComment(comment.getId()));
  }

  @Test
  void readCommentFAILURE_OPERATION() {
    Comment comment = new Comment(1L, "comment",new Book());
    when(crudCommentService.read(comment.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.readComment(comment.getId()));
  }

  @Test
  void readCommentILLEGAL_ARGUMENTS() {
    Comment comment = new Comment(0L, "comment",new Book());
    when(crudCommentService.read(comment.getId())).thenReturn(Optional.of(comment));

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.readComment(comment.getId()));
  }

  @Test
  void updateCommentSUCCESS_OPERATION() {
    Comment comment = new Comment(1L, "comment",new Book());
    when(crudCommentService.read(comment.getId())).thenReturn(Optional.of(comment));
    when(crudCommentService.update(comment)).thenReturn(comment);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.updateComment(comment.getId(), comment.getCommentary()));
  }

  @Test
  void updateCommentFAILURE_OPERATION() {
    Comment comment = new Comment(1L, "comment",new Book());
    when(crudCommentService.read(comment.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.updateComment(comment.getId(), comment.getCommentary()));
  }

  @Test
  void updateCommentILLEGAL_ARGUMENTS() {
    Comment comment = new Comment(0L, "comment",new Book());
    when(crudCommentService.read(comment.getId())).thenReturn(Optional.of(comment));

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.updateComment(comment.getId(), comment.getCommentary()));
  }

  @Test
  void deleteCommentILLEGAL_ARGUMENTS() {
    long commentId = 0L;

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.deleteComment(commentId));
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

  @Test
  void createBook() {
    List<String> values = new ArrayList<>();
    values.add("title");
    values.add("2020 01 01");
    Iterator<String> stringIterator = values.iterator();
    Mockito.when(inputReaderService.readLine()).thenAnswer(i ->stringIterator.next());
    List<String> values2 = new ArrayList<>();
    values2.add("1");
    values2.add("1");
    Iterator<String> stringIterator2 = values2.iterator();
    Mockito.when(inputReaderService.readToken()).thenAnswer(i ->stringIterator2.next());
    Mockito.when(crudBookService.create(any())).thenReturn(new Book());
    Mockito.when(crudAuthorService.read(1)).thenReturn(any());
    Mockito.when(crudGenreService.read(1)).thenReturn(any());

    Assertions.assertEquals(SUCCESS_OPERATION,shellLibraryController.createBook());

    verify(inputReaderService,times(2)).readLine();
    verify(inputReaderService,times(2)).readToken();
    verify(crudBookService,times(1)).create(any());
    verify(consolePrintService,times(4)).printlnMessage(anyString());
  }

  @Test
  void createBookWithThrows() {
    List<String> values = new ArrayList<>();
    values.add("title");
    values.add("2020-01-01");
    values.add("2020-01-01");
    Iterator<String> stringIterator = values.iterator();
    Mockito.when(inputReaderService.readLine()).thenAnswer(i ->stringIterator.next());
    Assertions.assertEquals(FAILURE_OPERATION,shellLibraryController.createBook());

    verify(inputReaderService,times(3)).readLine();
  }

  @Test
  void updateBookTitleSUCCESS_OPERATION() {
    Book book = new Book(1L,"title",convertStringToDate("2020 01 01"), new Author(), new Genre());
    Mockito.when(crudBookService.read(book.getId())).thenReturn(Optional.of(book));
    List<String> values = new ArrayList<>();
    values.add("newTitle");
    Iterator<String> stringIterator = values.iterator();
    Mockito.when(inputReaderService.readLine()).thenAnswer(i ->stringIterator.next());
    Mockito.when(crudBookService.update(any())).thenReturn(book);

    Assertions.assertEquals(SUCCESS_OPERATION,shellLibraryController.updateBookTitle(book.getId()));
    Assertions.assertEquals(values.get(0),book.getTitle());
  }

  @Test
  void updateBookTitleFAILURE_OPERATION() {
    long bookId = 1L;
    Mockito.when(crudBookService.read(bookId)).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION,shellLibraryController.updateBookTitle(bookId));
  }

  @Test
  void updateBookTitleILLEGAL_ARGUMENTS() {
    long bookId = 0L;
    Assertions.assertEquals(ILLEGAL_ARGUMENTS,shellLibraryController.updateBookTitle(bookId));
  }

  @Test
  void updateBookDateSUCCESS_OPERATION() {
    String newDate = "2020 10 10";
    Book book = new Book(1L,"title",convertStringToDate("2020 01 01"), new Author(), new Genre());
    Mockito.when(crudBookService.read(book.getId())).thenReturn(Optional.of(book));
    Mockito.when(inputReaderService.readLine()).thenReturn(newDate);
    Mockito.when(crudBookService.update(any())).thenReturn(book);

    Assertions.assertEquals(SUCCESS_OPERATION,shellLibraryController.updateBookDate(book.getId()));
    Assertions.assertEquals(convertStringToDate(newDate),book.getDate());
  }

  @Test
  void updateBookGenreSUCCESS_OPERATION() {
    Genre genre = new Genre(4L,"test");
    Book book = new Book(1L,"title",convertStringToDate("2020 01 01"), new Author(),genre );
    Mockito.when(crudBookService.read(book.getId())).thenReturn(Optional.of(book));
    Mockito.when(inputReaderService.readToken()).thenReturn(String.valueOf(genre.getId()));
    Mockito.when(crudBookService.update(any())).thenReturn(book);
    Mockito.when(crudGenreService.read(genre.getId())).thenReturn(Optional.of(genre));

    Assertions.assertEquals(SUCCESS_OPERATION,shellLibraryController.updateBookGenre(book.getId()));
    Assertions.assertEquals(genre.getId(),book.getGenre().getId());
  }

  @Test
  void updateBookAuthorSUCCESS_OPERATION() {
    Author author = new Author(4L,"FirstName","LastName",convertStringToDate("1988 09 19"));
    Book book = new Book(1L,"title",convertStringToDate("2020 01 01"), author,new Genre());
    Mockito.when(crudBookService.read(book.getId())).thenReturn(Optional.of(book));
    Mockito.when(inputReaderService.readToken()).thenReturn(String.valueOf(author.getId()));
    Mockito.when(crudBookService.update(any())).thenReturn(book);
    Mockito.when(crudAuthorService.read(author.getId())).thenReturn(Optional.of(author));

    Assertions.assertEquals(SUCCESS_OPERATION,shellLibraryController.updateBookAuthor(book.getId()));
    Assertions.assertEquals(author.getId(),book.getAuthor().getId());

    verify(crudAuthorService,times(1)).read(author.getId());
  }
}
