package ru.otus.hw10.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw10.model.Genre;
import ru.otus.hw10.repository.GenreRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@ComponentScan({"ru.otus.hw10.test.config.mongock", "ru.otus.hw10.repository"})
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
