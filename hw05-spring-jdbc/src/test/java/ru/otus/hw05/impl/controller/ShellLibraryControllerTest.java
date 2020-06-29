package ru.otus.hw05.impl.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw05.core.service.ExecutorDaoService;
import ru.otus.hw05.core.service.ViewDaoService;
import ru.otus.hw05.impl.service.ExecutorDaoServiceImpl;

import java.text.ParseException;

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

  @Test
  @DisplayName("Expect illegal arguments")
  void insertBookIllegalArguments() {
    String expected = "Illegal Arguments";

    String actual = shellLibraryController.insertBook("","",0,0);

    Assertions.assertEquals(expected,actual);
  }

  @Test
  @DisplayName("Expect failure insert book")
  void insertBookFailureInsertBook() {
    String expected = "Failure insert book";

    String actual = shellLibraryController.insertBook("Test","2020-01-01",10,10);

    Assertions.assertEquals(expected,actual);
  }

  @Test
  @DisplayName("Expect success insert book")
  void insertBookSuccessInsertBook() {
    String expected = "Success insert book";
    Mockito.when(executorDaoService.insertBook(any())).thenReturn(true);

    String actual = shellLibraryController.insertBook("Test","2020-01-01",1,1);

    Assertions.assertEquals(expected,actual);
  }

  @Test
  void insertBookThrowParseDateToString() {
    assertThrows(ParseException.class, () -> {
      shellLibraryController.insertBook("Test","20200101",1,1);
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
}
