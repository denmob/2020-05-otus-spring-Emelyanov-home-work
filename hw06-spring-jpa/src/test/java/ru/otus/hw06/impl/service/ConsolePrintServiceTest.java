package ru.otus.hw06.impl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConsolePrintServiceTest {

  private ConsolePrintService outputPrinterServiceImpl;
  private OutputStream outputStream;

  @BeforeEach
  void beforeEach() {
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    outputPrinterServiceImpl = new ConsolePrintService();
  }

  @Test
  void printlnMessage1() {
    outputPrinterServiceImpl.printlnMessage("hello");
    assertEquals("hello\r\n", outputStream.toString());
  }
}
