package ru.otus.hw04.impl.service;

import org.junit.jupiter.api.*;
import ru.otus.hw04.core.service.InputReaderService;

import java.io.ByteArrayInputStream;

class InputReaderServiceImplTest {

  private InputReaderService inputReaderServiceImpl;

  @Test
  @DisplayName("readAnswer return mock scanner with 1 ")
  void readAnswerInputInteger() {
    System.setIn(new ByteArrayInputStream("1".getBytes()));
    inputReaderServiceImpl = new InputReaderServiceImpl();

    Assertions.assertEquals("1", inputReaderServiceImpl.readAnswer());
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
