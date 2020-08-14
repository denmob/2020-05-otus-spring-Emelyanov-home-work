package ru.otus.hw03.impl.service;

import org.springframework.stereotype.Service;
import ru.otus.hw03.core.service.OutputPrinterService;

import java.io.PrintStream;

@Service
public class OutputPrinterServiceImpl implements OutputPrinterService {

  private final PrintStream printStream = new PrintStream(System.out);

  @Override
  public void printlnMessage(String message) {
    printStream.println(message);
  }
}
