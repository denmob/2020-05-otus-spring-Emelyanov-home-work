package ru.otus.hw07;

import lombok.SneakyThrows;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hw07Application {

  @SneakyThrows
  public static void main(String[] args) {
    SpringApplication.run(Hw07Application.class, args);
    Console.main(args);
  }
}

