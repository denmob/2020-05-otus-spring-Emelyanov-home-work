package ru.otus.hw04.impl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw04.core.service.OutputPrinterService;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class OutputPrinterServiceImplTest {

  private OutputPrinterService outputPrinterServiceImpl;
  private OutputStream outputStream;

  @BeforeEach
  void beforeEach() {
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
  }

  @Test
  void printlnMessage1() {
    outputPrinterServiceImpl = new OutputPrinterServiceImpl();
    outputPrinterServiceImpl.printlnMessage("hello");
    assertEquals("hello\r\n", outputStream.toString());
  }

  @Test
  void printlnMessageEmpty() {
    outputPrinterServiceImpl = new OutputPrinterServiceImpl();
    outputPrinterServiceImpl.printlnMessage("");
    assertEquals("\r\n", outputStream.toString());
  }
}
