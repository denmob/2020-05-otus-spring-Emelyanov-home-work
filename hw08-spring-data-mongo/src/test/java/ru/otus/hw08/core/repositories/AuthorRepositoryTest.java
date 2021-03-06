package ru.otus.hw08.core.repositories;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw08.core.models.Author;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@ComponentScan({"ru.otus.hw08.config.mongock", "ru.otus.hw08.core.repositories"})
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
