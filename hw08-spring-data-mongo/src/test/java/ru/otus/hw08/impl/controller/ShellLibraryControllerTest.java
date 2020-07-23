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
import ru.otus.hw08.impl.service.*;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@EnableConfigurationProperties
@SpringBootTest(classes = {ManagerDataServiceImpl.class, InputReaderServiceImpl.class, ConsolePrintService.class, ShellLibraryController.class})
class ShellLibraryControllerTest {

  @MockBean
  private ManagerDataServiceImpl managerDataService;

  @MockBean
  private InputReaderServiceImpl inputReaderService;

  @MockBean
  private ConsolePrintService consolePrintService;


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
    newComment = Comment.builder().id("").commentary("new comment").bookId(oldBook.getId()).build();
    oldComment = new Comment("1", "old comment", oldBook.getId(), new Date());
  }

  @Test
  void readBookSuccessOperation() {
    BookWithComments bookWithComments = new BookWithComments(oldBook, null);
    when(managerDataService.readBookByTitle(oldBook.getId())).thenReturn(Optional.of(bookWithComments));

    Assertions.assertEquals(bookWithComments.toString(), shellLibraryController.readBook(oldBook.getId()));
  }

  @Test
  void readBookFailureOperation() {
    when(managerDataService.readBookByTitle(oldBook.getId())).thenReturn(Optional.empty());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.readBook(oldBook.getId()));
  }

  @Test
  void readBookIllegalArguments() {
    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.readBook(newBook.getId()));
  }

  @Test
  void deleteBookSuccessOperation() {
    when(managerDataService.deleteBookByTitle(oldBook.getTitle())).thenReturn(true);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.deleteBook(oldBook.getTitle()));
  }

  @Test
  void deleteBookIllegalArguments() {
    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.deleteBook(newBook.getId()));
  }

  @Test
  void createCommentSuccessOperation() {
    when(managerDataService.createComment(oldBook.getTitle(), newComment.getCommentary())).thenReturn(true);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.createComment(oldBook.getTitle(), newComment.getCommentary()));
  }

  @Test
  void createCommentIllegalArguments() {
    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.createComment(oldBook.getTitle(), ""));
  }

  @Test
  void createCommentFailureOperation() {
    when(managerDataService.createComment(oldBook.getTitle(), newComment.getCommentary())).thenReturn(false);

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.createComment(oldBook.getId(), newComment.getCommentary()));
  }

  @Test
  void readCommentSuccessOperation() {
    List<Comment> comments = new ArrayList<>();
    comments.add(oldComment);
    when(managerDataService.readComments(oldComment.getCommentary())).thenReturn(comments);

    Assertions.assertEquals(oldComment.toString(), shellLibraryController.readComment(oldComment.getCommentary()));
  }

  @Test
  void readCommentFailureOperation() {
    when(managerDataService.readComments(oldComment.getId())).thenReturn(new ArrayList<>());

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.readComment(oldComment.getId()));
  }

  @Test
  void readCommentIllegalArguments() {
    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.readComment(""));
  }


  @Test
  void deleteCommentIllegalArguments() {
    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.deleteComment(newComment.getId()));
  }

  @Test
  void deleteCommentSuccessOperation() {
    when(managerDataService.deleteComment(oldComment.getId())).thenReturn(true);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.deleteComment(oldComment.getId()));
  }

  @Test
  void printTableBooks() {
    Mockito.doNothing().when(managerDataService).printTableBooks();

    shellLibraryController.printTableBooks();

    Mockito.verify(managerDataService, Mockito.times(1)).printTableBooks();
  }

  @Test
  void printTableAuthors() {
    Mockito.doNothing().when(managerDataService).printTableAuthors();

    shellLibraryController.printTableAuthors();

    Mockito.verify(managerDataService, Mockito.times(1)).printTableAuthors();
  }

  @Test
  void printTableGenres() {
    Mockito.doNothing().when(managerDataService).printTableGenres();

    shellLibraryController.printTableGenres();

    Mockito.verify(managerDataService, Mockito.times(1)).printTableGenres();
  }

  @Test
  void printTableComments() {
    Mockito.doNothing().when(managerDataService).printTableComments();

    shellLibraryController.printTableComments();

    Mockito.verify(managerDataService, Mockito.times(1)).printTableComments();
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
    Mockito.when(managerDataService.createBook(any())).thenReturn(true);
    Mockito.when(managerDataService.readAuthorByLastName("1")).thenReturn(any());
    Mockito.when(managerDataService.readGenreByName("1")).thenReturn(any());

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.createBook());

    verify(inputReaderService, times(2)).readLine();
    verify(inputReaderService, times(2)).readToken();
    verify(managerDataService, times(1)).createBook(any());
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

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return new SimpleDateFormat("yyyy MM dd").parse(date);
  }
}
