package ru.otus.hw06.impl.service;

import org.springframework.stereotype.Service;
import ru.otus.hw06.core.service.OutputPrintService;

import java.io.PrintStream;

@Service
public class OutputPrintServiceImpl implements OutputPrintService {

  private final PrintStream printStream = new PrintStream(System.out);

  @Override
  public void printlnMessage(String message) {
    printStream.println(message);
  }
}
