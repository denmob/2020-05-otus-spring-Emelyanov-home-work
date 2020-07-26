package ru.otus.hw08.impl.service;

import org.springframework.stereotype.Service;
import ru.otus.hw08.core.service.InputReaderService;

import java.util.Scanner;

@Service
public class InputReaderServiceImpl implements InputReaderService {

  private final Scanner scanner = new Scanner(System.in);

  @Override
  public String readToken() {
    return scanner.next();
  }

  @Override
  public String readLine() {
    return scanner.nextLine();
  }
}
