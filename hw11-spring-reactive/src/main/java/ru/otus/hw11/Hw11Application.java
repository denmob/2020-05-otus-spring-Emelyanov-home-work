package ru.otus.hw11;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableConfigurationProperties(ThymeleafProperties.class)
public class Hw11Application {

  @SneakyThrows
  public static void main(String[] args) {
    SpringApplication.run(Hw11Application.class, args);
  }
}

