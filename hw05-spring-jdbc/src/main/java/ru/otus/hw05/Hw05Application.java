package ru.otus.hw05;

import lombok.SneakyThrows;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hw05Application {

  @SneakyThrows
  public static void main(String[] args) {
    SpringApplication.run(Hw05Application.class, args);
    Console.main(args);
  }
}

