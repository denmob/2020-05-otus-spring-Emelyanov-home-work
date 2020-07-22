package ru.otus.hw08.impl.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw08.core.dto.BookWithComments;
import ru.otus.hw08.core.models.Author;
import ru.otus.hw08.core.models.Book;
import ru.otus.hw08.core.models.Comment;
import ru.otus.hw08.core.models.Genre;
import ru.otus.hw08.core.service.ViewRepositoryService;
import ru.otus.hw08.impl.service.*;
import ru.otus.hw08.impl.service.CRUDBookService;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@EnableConfigurationProperties
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

  private static final String SUCCESS_OPERATION = "Success operation";
  private static final String FAILURE_OPERATION = "Failure operation";
  private static final String ILLEGAL_ARGUMENTS = "Illegal Arguments";

  @Autowired
  private ShellLibraryController shellLibraryController;

  private Book newBook;
  private Book oldBook;
  private Comment newComment;
  private Comment oldComment;

  @BeforeEach
  void beforeEach() {
    Author author = new Author("4", "FirstName", "LastName", convertStringToDate("1988 09 19"));
    Genre genre = new Genre("4", "test");
    newBook = new Book("", "Title new", convertStringToDate("2020 02 01"), new Author(), new Genre());
    oldBook = new Book("4", "Title old", convertStringToDate("2020 02 01"), author, genre);
    newComment =  Comment.builder().id("").commentary("new comment").bookId(oldBook.getId()).build();
    oldComment = new Comment("1", "old comment", oldBook.getId());
  }

  @Test
  void readBookSuccessOperation() {
    BookWithComments bookWithComments = new BookWithComments(oldBook, null);
    when(crudBookService.readWithComments(oldBook.getId())).thenReturn(Optional.of(bookWithComments));

    Assertions.assertEquals(bookWithComments.toString(), shellLibraryController.readBook(oldBook.getId()));
  }

  @Test
  void readBookFailureOperation() {
    when(crudBookService.read(oldBook.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.readBook(oldBook.getId()));
  }

  @Test
  void readBookIllegalArguments() {
    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.readBook(newBook.getId()));
  }

  @Test
  void deleteBookSuccessOperation() {
    doNothing().when(crudBookService).delete(oldBook.getId());

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.deleteBook(oldBook.getId()));
  }

  @Test
  void deleteBookIllegalArguments() {
    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.deleteBook(newBook.getId()));
  }

  @Test
  void createCommentSuccessOperation() {
    when(crudBookService.read(oldBook.getId())).thenReturn(java.util.Optional.of(oldBook));
    when(crudCommentService.create(any())).thenReturn(newComment);
  //  when(crudBookService.update(oldBook)).thenReturn(oldBook);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.createComment(oldBook.getId(), newComment.getCommentary()));
  }

  @Test
  void createCommentIllegalArguments() {
    String bookId = "0";
    when(crudBookService.read(bookId)).thenReturn(java.util.Optional.empty());

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.createComment(bookId, ""));
  }

  @Test
  void createCommentFailureOperation() {
    when(crudBookService.read(oldBook.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.createComment(oldBook.getId(), "comment fail"));
  }

  @Test
  void readCommentSuccessOperation() {
    when(crudCommentService.read(oldComment.getId())).thenReturn(java.util.Optional.of(oldComment));

    Assertions.assertEquals(oldComment.toString(), shellLibraryController.readComment(oldComment.getId()));
  }

  @Test
  void readCommentFailureOperation() {
    when(crudCommentService.read(oldComment.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.readComment(oldComment.getId()));
  }

  @Test
  void readCommentIllegalArguments() {
    when(crudCommentService.read(newComment.getId())).thenReturn(Optional.of(newComment));

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.readComment(newComment.getId()));
  }

  @Test
  void updateCommentSuccessOperation() {
    when(crudCommentService.read(oldComment.getId())).thenReturn(Optional.of(oldComment));
    when(crudCommentService.update(oldComment)).thenReturn(oldComment);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.updateComment(oldComment.getId(), oldComment.getCommentary()));
  }

  @Test
  void updateCommentFailureOperation() {
    when(crudCommentService.read(oldComment.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.updateComment(oldComment.getId(), oldComment.getCommentary()));
  }

  @Test
  void updateCommentIllegalArguments() {
    when(crudCommentService.read(newComment.getId())).thenReturn(Optional.of(newComment));

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.updateComment(newComment.getId(), newComment.getCommentary()));
  }

  @Test
  void deleteCommentIllegalArguments() {
    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.deleteComment(newComment.getId()));
  }

  @Test
  void deleteCommentSuccessOperation() {
    doNothing().when(crudCommentService).delete(oldComment.getId());

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.deleteComment(oldComment.getId()));
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


  @Test
  void createBook() {
    List<String> values = new ArrayList<>();
    values.add("title");
    values.add("2020 01 01");
    Iterator<String> stringIterator = values.iterator();
    Mockito.when(inputReaderService.readLine()).thenAnswer(i -> stringIterator.next());
    List<String> values2 = new ArrayList<>();
    values2.add("1");
    values2.add("1");
    Iterator<String> stringIterator2 = values2.iterator();
    Mockito.when(inputReaderService.readToken()).thenAnswer(i -> stringIterator2.next());
    Mockito.when(crudBookService.create(any())).thenReturn(new Book());
    Mockito.when(crudAuthorService.read("1")).thenReturn(any());
    Mockito.when(crudGenreService.read("1")).thenReturn(any());

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.createBook());

    verify(inputReaderService, times(2)).readLine();
    verify(inputReaderService, times(2)).readToken();
    verify(crudBookService, times(1)).create(any());
    verify(consolePrintService, times(4)).printlnMessage(anyString());
  }

  @Test
  void createBookWithThrows() {
    List<String> values = new ArrayList<>();
    values.add("title345");
    values.add("2020-01-01");
    values.add("2020-01-01");
    Iterator<String> stringIterator = values.iterator();
    Mockito.when(inputReaderService.readLine()).thenAnswer(i -> stringIterator.next());
    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.createBook());

    verify(inputReaderService, times(3)).readLine();
  }

  @Test
  void updateBookTitleSuccessOperation() {
    Mockito.when(crudBookService.read(oldBook.getId())).thenReturn(Optional.of(oldBook));
    List<String> values = new ArrayList<>();
    values.add("newTitle");
    Iterator<String> stringIterator = values.iterator();
    Mockito.when(inputReaderService.readLine()).thenAnswer(i -> stringIterator.next());
    Mockito.when(crudBookService.update(any())).thenReturn(oldBook);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.updateBookTitle(oldBook.getId()));
    Assertions.assertEquals(values.get(0), oldBook.getTitle());
  }

  @Test
  void updateBookTitleFailureOperation() {
    Mockito.when(crudBookService.read(oldBook.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.updateBookTitle(oldBook.getId()));
  }

  @Test
  void updateBookTitleIllegalArguments() {
    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.updateBookTitle(newBook.getId()));
  }

  @Test
  void updateBookDateSuccessOperation() {
    String newDate = "2020 10 10";
    Mockito.when(crudBookService.read(oldBook.getId())).thenReturn(Optional.of(oldBook));
    Mockito.when(inputReaderService.readLine()).thenReturn(newDate);
    Mockito.when(crudBookService.update(any())).thenReturn(oldBook);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.updateBookDate(oldBook.getId()));
    Assertions.assertEquals(convertStringToDate(newDate), oldBook.getDate());
  }

  @Test
  void updateBookGenreSuccessOperation() {
    Mockito.when(crudBookService.read(oldBook.getId())).thenReturn(Optional.of(oldBook));
    Mockito.when(inputReaderService.readToken()).thenReturn(String.valueOf(oldBook.getGenre().getId()));
    Mockito.when(crudBookService.update(any())).thenReturn(oldBook);
    Mockito.when(crudGenreService.read(oldBook.getGenre().getId())).thenReturn(Optional.of(oldBook.getGenre()));

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.updateBookGenre(oldBook.getId()));
    Assertions.assertEquals(oldBook.getGenre().getId(), oldBook.getGenre().getId());
  }

  @Test
  void updateBookAuthorSuccessOperation() {
    Mockito.when(crudBookService.read(oldBook.getId())).thenReturn(Optional.of(oldBook));
    Mockito.when(inputReaderService.readToken()).thenReturn(String.valueOf(oldBook.getAuthor().getId()));
    Mockito.when(crudBookService.update(any())).thenReturn(oldBook);
    Mockito.when(crudAuthorService.read(oldBook.getAuthor().getId())).thenReturn(Optional.of(oldBook.getAuthor()));

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.updateBookAuthor(oldBook.getId()));
    Assertions.assertEquals(oldBook.getAuthor().getId(), oldBook.getAuthor().getId());

    verify(crudAuthorService, times(1)).read(oldBook.getAuthor().getId());
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return new SimpleDateFormat("yyyy MM dd").parse(date);
  }
}
