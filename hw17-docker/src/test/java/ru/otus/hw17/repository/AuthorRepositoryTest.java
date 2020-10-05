package ru.otus.hw17.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw17.config.MongoConfig;
import ru.otus.hw17.model.Author;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@ComponentScan({"ru.otus.hw17.repository"})
@Import(MongoConfig.class)
class AuthorRepositoryTest {

  @Autowired
  private AuthorRepository authorRepository;

  private final String existLastName = "Langr";
  private final String nonExistLastName = "nonExistLastName";

  @Test
  @DisplayName("success findByLastNameEquals")
  void findByLastNameEqualsSuccess() {
    Optional<Author> authorOptional = authorRepository.findByLastNameEquals(existLastName);
    assertTrue(authorOptional.isPresent());
    assertEquals(existLastName, authorOptional.get().getLastName());
  }

  @Test
  @DisplayName("failure findByLastNameEquals")
  void findByLastNameEqualsFailure() {
    Optional<Author> authorOptional = authorRepository.findByLastNameEquals(nonExistLastName);
    assertFalse(authorOptional.isPresent());
  }

  @Test
  @DisplayName("success deleteAuthorByLastNameEquals")
  @DirtiesContext
  void deleteAuthorByLastNameEqualsSuccess() {
    assertEquals(1L, authorRepository.deleteAuthorByLastNameEquals(existLastName));
  }

  @Test
  @DisplayName("failure deleteAuthorByLastNameEquals")
  void deleteAuthorByLastNameEqualsFailure() {
    assertEquals(0L, authorRepository.deleteAuthorByLastNameEquals(nonExistLastName));
  }

  @Test
  @DirtiesContext
  @DisplayName("success deleteAuthorById")
  void deleteAuthorById() {
    Optional<Author> authorOptional = authorRepository.findByLastNameEquals(existLastName);
    assertEquals(1L, authorRepository.deleteAuthorById(authorOptional.get().getId()));
  }
}
