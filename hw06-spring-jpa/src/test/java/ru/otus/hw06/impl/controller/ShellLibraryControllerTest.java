package ru.otus.hw06.impl.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.service.ExecutorDaoService;
import ru.otus.hw06.core.service.ViewDaoService;
import ru.otus.hw06.impl.service.ExecutorDaoServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = {ShellLibraryController.class, ExecutorDaoServiceImpl.class})
class ShellLibraryControllerTest {

  @MockBean
  private ExecutorDaoService executorDaoService;

  @MockBean
  private ViewDaoService viewDaoService;

  @Autowired
  private ShellLibraryController shellLibraryController;

  private static final String SUCCESS_OPERATION = "Success operation";
  private static final String FAILURE_OPERATION = "Failure operation";
  private static final String ILLEGAL_ARGUMENTS = "Illegal Arguments";

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @Test
  @DisplayName("Expect illegal arguments")
  void insertBookIllegalArguments() {
    String actual = shellLibraryController.insertBook("", "", 0, 0);

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, actual);
  }

  @Test
  @DisplayName("Expect failure insert book")
  void insertBookFailure() {
    String actual = shellLibraryController.insertBook("Test", "2020-01-01", 10, 10);

    Assertions.assertEquals(FAILURE_OPERATION, actual);
  }

  @Test
  @DisplayName("Expect success insert book")
  void insertBookSuccess() {
    Mockito.when(executorDaoService.insertBook(any())).thenReturn(true);

    String actual = shellLibraryController.insertBook("Test", "2020-01-01", 1, 1);

    Assertions.assertEquals(SUCCESS_OPERATION, actual);
  }

  @Test
  void insertBookThrowParseDateToString() {
    assertThrows(ParseException.class, () -> {
      shellLibraryController.insertBook("Test", "20200101", 1, 1);
    });
  }

  @Test
  void printTableBooks() {
    Mockito.doNothing().when(viewDaoService).printTableBooks();

    shellLibraryController.printTableBooks();

    Mockito.verify(viewDaoService, Mockito.times(1)).printTableBooks();
  }

  @Test
  void printTableAuthors() {
    Mockito.doNothing().when(viewDaoService).printTableAuthors();

    shellLibraryController.printTableAuthors();

    Mockito.verify(viewDaoService, Mockito.times(1)).printTableAuthors();
  }

  @Test
  void printTableGenres() {
    Mockito.doNothing().when(viewDaoService).printTableGenres();

    shellLibraryController.printTableGenres();

    Mockito.verify(viewDaoService, Mockito.times(1)).printTableGenres();
  }

  @Test
  void deleteBookSuccess() {
    long id = 1L;
    Mockito.when(executorDaoService.deleteBook(id)).thenReturn(true);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.deleteBook(id));

    Mockito.verify(executorDaoService, Mockito.times(1)).deleteBook(id);
  }

  @Test
  void deleteBookFailure() {
    long id = 1L;
    Mockito.when(executorDaoService.deleteBook(id)).thenReturn(false);

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.deleteBook(id));

    Mockito.verify(executorDaoService, Mockito.times(1)).deleteBook(id);
  }

  @Test
  void insertAuthorSuccess() {
    String birthDay = "2020-01-01";
    Author author = new Author();
    author.setFirstName("FirstName");
    author.setLastName("LastName");
    author.setBirthday(convertStringToDate(birthDay));

    Mockito.when(executorDaoService.insertAuthor(author)).thenReturn(true);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.insertAuthor(author.getFirstName(), author.getLastName(), birthDay));
  }

  @Test
  void insertAuthorFailure() {
    String birthDay = "2020-01-01";
    Author author = new Author();
    author.setFirstName("FirstName");
    author.setLastName("LastName");
    author.setBirthday(convertStringToDate(birthDay));

    Mockito.when(executorDaoService.insertAuthor(author)).thenReturn(false);

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.insertAuthor(author.getFirstName(), author.getLastName(), birthDay));
  }

  @Test
  void insertAuthorIllegal() {
    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.insertAuthor("", "", "new Date())"));
  }

  @Test
  void deleteAuthorSuccess() {
    long id = 1L;
    Mockito.when(executorDaoService.deleteAuthor(id)).thenReturn(true);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.deleteAuthor(id));

    Mockito.verify(executorDaoService, Mockito.times(1)).deleteAuthor(id);
  }

  @Test
  void deleteAuthorFailure() {
    long id = 1L;
    Mockito.when(executorDaoService.deleteAuthor(id)).thenReturn(false);

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.deleteAuthor(id));

    Mockito.verify(executorDaoService, Mockito.times(1)).deleteAuthor(id);
  }

  @Test
  void insertGenreSuccess() {
    Genre genre = new Genre();
    genre.setName("newGenre");

    Mockito.when(executorDaoService.insertGenre(genre)).thenReturn(true);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.insertGenre(genre.getName()));
  }

  @Test
  void insertGenreFailure() {
    Genre genre = new Genre();
    genre.setName("newGenre");

    Mockito.when(executorDaoService.insertGenre(genre)).thenReturn(false);

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.insertGenre(genre.getName()));
  }

  @Test
  void insertGenreIllegal() {
    Genre genre = new Genre();
    genre.setName("");

    Assertions.assertEquals(ILLEGAL_ARGUMENTS, shellLibraryController.insertGenre(genre.getName()));
  }

  @Test
  void deleteGenreSuccess() {
    long id = 1L;
    Mockito.when(executorDaoService.deleteGenre(id)).thenReturn(true);

    Assertions.assertEquals(SUCCESS_OPERATION, shellLibraryController.deleteGenre(id));

    Mockito.verify(executorDaoService, Mockito.times(1)).deleteGenre(id);
  }

  @Test
  void deleteGenreFailure() {
    long id = 1L;
    Mockito.when(executorDaoService.deleteGenre(id)).thenReturn(false);

    Assertions.assertEquals(FAILURE_OPERATION, shellLibraryController.deleteGenre(id));

    Mockito.verify(executorDaoService, Mockito.times(1)).deleteGenre(id);
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return SIMPLE_DATE_FORMAT.parse(date);
  }


}
