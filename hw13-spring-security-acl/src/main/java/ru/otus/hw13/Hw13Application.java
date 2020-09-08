package ru.otus.hw13;

import com.github.cloudyrock.spring.v5.EnableMongock;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@SpringBootApplication
@EnableMongoRepositories
@EnableConfigurationProperties
public class Hw13Application {

  @SneakyThrows
  public static void main(String[] args) {
    SpringApplication.run(Hw13Application.class, args);
  }
}

