package ru.otus.hw11.repository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@ComponentScan({"ru.otus.hw11.test.config.mongock", "ru.otus.hw11.repository"})
class AuthorRepositoryTest {

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Autowired
  private AuthorRepository authorRepository;

  @Test
  void findAll() {
    StepVerifier
        .create(authorRepository.findAll())
        .expectNextMatches(author1 -> author1.getFirstName().equals("Jeff(test)"))
        .expectNextMatches(author2 -> author2.getLastName().equals("Bloch"))
        .expectNextMatches(author3 -> author3.getBirthday().equals(convertStringToDate("1959-03-19")))
        .expectNextCount(0)
        .expectComplete()
        .verify();
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return dateFormat.parse(date);
  }
}
