package ru.otus.hw05;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class Hw05Application {

  public static void main(String[] args) throws SQLException {
    SpringApplication.run(Hw05Application.class, args);
    Console.main(args);
  }
}

