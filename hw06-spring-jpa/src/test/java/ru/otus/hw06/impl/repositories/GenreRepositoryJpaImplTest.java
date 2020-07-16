package ru.otus.hw06.impl.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.hw06.core.models.Genre;

import java.util.Optional;

@DataJpaTest
@Sql("classpath:data-test.sql")
@Import(GenreRepositoryJpaImpl.class)
class GenreRepositoryJpaImplTest {

  @Autowired
  private GenreRepositoryJpaImpl genreRepositoryJpa;

  @Autowired
  private TestEntityManager testEntityManager;

  @BeforeEach
  void beforeEach() {
    testEntityManager.clear();
  }

  @Test
  void count() {
    Assertions.assertEquals(3L, genreRepositoryJpa.count());
  }

  @Test
  void insertNewGenre() {
    Genre genre = new Genre(0L,"testGenre");
    genreRepositoryJpa.insert(genre);
    Assertions.assertEquals(4L, genreRepositoryJpa.count());
  }

  @Test
  void UpdateGenre() {
    Genre genre = new Genre(3L,"testGenre");
    genreRepositoryJpa.insert(genre);
    Assertions.assertEquals(3L, genreRepositoryJpa.count());
  }

  @Test
  void getById() {
    Optional<Genre> optionalGenre = genreRepositoryJpa.getById(3L);
    Assertions.assertTrue(optionalGenre.isPresent());
    Assertions.assertEquals("Software",optionalGenre.get().getName());
  }

  @Test
  void getAll() {
    Assertions.assertEquals(3, genreRepositoryJpa.getAll().size());
  }

  @Test
  void deleteById() {
    genreRepositoryJpa.deleteById(3L);
    Assertions.assertEquals(2, genreRepositoryJpa.getAll().size());
  }
}
