package ru.otus.web;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableConfigurationProperties
public class WebServiceApplication {

  @SneakyThrows
  public static void main(String[] args) {
    SpringApplication.run(WebServiceApplication.class, args);
  }
}

