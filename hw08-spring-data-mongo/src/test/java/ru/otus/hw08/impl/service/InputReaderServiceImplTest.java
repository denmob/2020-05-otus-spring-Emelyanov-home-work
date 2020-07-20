package ru.otus.hw08.impl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw08.core.service.InputReaderService;


import java.io.ByteArrayInputStream;

@SpringBootTest(classes = InputReaderServiceImpl.class)
class InputReaderServiceImplTest {

  private InputReaderService inputReaderServiceImpl;

  @Test
  @DisplayName("readToken return mock scanner with 1 ")
  void readTokenInputInteger() {
    System.setIn(new ByteArrayInputStream("1".getBytes()));
    inputReaderServiceImpl = new InputReaderServiceImpl();

    Assertions.assertEquals("1", inputReaderServiceImpl.readToken());
  }

  @Test
  @DisplayName("read input line with mock scanner")
  void readLine() {
    String expected = "Den";
    System.setIn(new ByteArrayInputStream(expected.getBytes()));
    inputReaderServiceImpl = new InputReaderServiceImpl();

    Assertions.assertEquals(expected, inputReaderServiceImpl.readLine());
  }
}
