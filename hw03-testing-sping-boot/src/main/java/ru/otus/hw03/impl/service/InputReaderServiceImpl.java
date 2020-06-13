package ru.otus.hw03.impl.service;

import org.springframework.stereotype.Service;
import ru.otus.hw03.core.service.InputReaderService;
import java.util.Scanner;


@Service
public class InputReaderServiceImpl implements InputReaderService {

  private final Scanner scanner = new Scanner(System.in);

  @Override
  public int readAnswer() {
      return scanner.nextInt();
  }
}
