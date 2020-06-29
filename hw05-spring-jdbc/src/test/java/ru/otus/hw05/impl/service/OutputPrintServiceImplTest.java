package ru.otus.hw05.impl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class OutputPrintServiceImplTest {

  private OutputPrintServiceImpl outputPrinterServiceImpl;
  private OutputStream outputStream;

  @BeforeEach
  void beforeEach() {
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    outputPrinterServiceImpl = new OutputPrintServiceImpl();
  }

  @Test
  void printlnMessage1() {
    outputPrinterServiceImpl.printlnMessage("hello");
    assertEquals("hello\r\n", outputStream.toString());
  }
}
