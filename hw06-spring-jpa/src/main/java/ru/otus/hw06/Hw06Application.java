package ru.otus.hw06;

import lombok.SneakyThrows;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hw06Application {

  @SneakyThrows
  public static void main(String[] args) {
    SpringApplication.run(Hw06Application.class, args);
    Console.main(args);
  }
}

