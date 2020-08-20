package ru.otus.hw11.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@ComponentScan({"ru.otus.hw11.test.config.mongock", "ru.otus.hw11.repository"})
class GenreRepositoryTest {

  @Autowired
  private GenreRepository genreRepository;

  @Test
  void findAll() {
    StepVerifier
        .create(genreRepository.findAll())
        .expectNextMatches(genre -> genre.getName().equals("Programming(test)"))
        .expectNextCount(2)
        .expectComplete()
        .verify();
  }
}
