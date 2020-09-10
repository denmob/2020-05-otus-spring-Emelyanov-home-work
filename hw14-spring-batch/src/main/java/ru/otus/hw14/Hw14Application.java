package ru.otus.hw14;

import com.github.cloudyrock.spring.v5.EnableMongock;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.otus.hw14.repository.crud")
@EnableMongoRepositories(basePackages = "ru.otus.hw14.repository.mongo")
@EnableConfigurationProperties
public class Hw14Application {

  @SneakyThrows
  public static void main(String[] args) {
    SpringApplication.run(Hw14Application.class, args);
  }
}

