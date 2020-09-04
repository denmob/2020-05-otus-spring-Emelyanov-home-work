package ru.otus.hw13.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw13.model.Genre;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@ComponentScan({"ru.otus.hw13.config", "ru.otus.hw13.repository"})
class GenreRepositoryTest {

  @Autowired
  private GenreRepository genreRepository;

  private final String existName = "Programming(test)";
  private final String nonExistName = "nonExistLastName";

  @Test
  void findByNameEqualsExistName() {
    Optional<Genre> optionalGenre = genreRepository.findByNameEquals(existName);
    assertTrue(optionalGenre.isPresent());
    assertEquals(existName, optionalGenre.get().getName());
  }

  @Test
  void findByNameEqualsNonExistName() {
    Optional<Genre> optionalGenre = genreRepository.findByNameEquals(nonExistName);
    assertFalse(optionalGenre.isPresent());
  }

  @Test
  @DirtiesContext
  void deleteAllByNameEqualsExistName() {
    assertEquals(1L, (long) genreRepository.deleteByNameEquals(existName));
  }

  @Test
  void deleteAllByNameEqualsNonExistName() {
    assertEquals(0L, (long) genreRepository.deleteByNameEquals(nonExistName));
  }


}
