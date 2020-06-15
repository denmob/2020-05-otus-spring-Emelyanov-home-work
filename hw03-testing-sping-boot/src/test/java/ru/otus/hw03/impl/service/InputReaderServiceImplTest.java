package ru.otus.hw03.impl.service;

import org.junit.jupiter.api.*;
import ru.otus.hw03.core.service.InputReaderService;

import java.io.ByteArrayInputStream;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InputReaderServiceImplTest {

  private InputReaderService inputReaderServiceImpl;

  @Test
  @DisplayName("readAnswer return mock scanner with 1 ")
  void readAnswerInputInteger() {
    System.setIn(new ByteArrayInputStream("1".getBytes()));
    inputReaderServiceImpl = new InputReaderServiceImpl();

    Assertions.assertEquals(1, inputReaderServiceImpl.readAnswer());
  }

  @Test
  @DisplayName("readAnswer return mock scanner with t")
  void readAnswerInputChar() {
    System.setIn(new ByteArrayInputStream("t".getBytes()));
    inputReaderServiceImpl = new InputReaderServiceImpl();

    assertThrows(InputMismatchException.class, () -> inputReaderServiceImpl.readAnswer());
  }

  @Test
  @DisplayName("read input line with mock scanner")
  void readName() {
    String expected = "Den";
    System.setIn(new ByteArrayInputStream(expected.getBytes()));
    inputReaderServiceImpl = new InputReaderServiceImpl();

    Assertions.assertEquals(expected, inputReaderServiceImpl.readName());
  }

}
