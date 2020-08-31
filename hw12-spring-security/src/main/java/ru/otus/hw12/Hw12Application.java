package ru.otus.hw12;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableConfigurationProperties
public class Hw12Application {

  @SneakyThrows
  public static void main(String[] args) {
    SpringApplication.run(Hw12Application.class, args);
  }
}

