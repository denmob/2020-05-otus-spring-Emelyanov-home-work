package ru.otus.hw06.impl.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw06.core.models.Genre;

import java.util.List;
import java.util.Optional;

@JdbcTest
@Import({GenreRepositoryJpaImpl.class})
class GenreRepositoryJpaImplTest {

  @Autowired
  private GenreRepositoryJpaImpl genreDaoJdbc;

  @Test
  void count() {
    Assertions.assertEquals(3, genreDaoJdbc.count());
  }

  @Test
  void insert() {
    Genre genre = new Genre();
    genre.setName("testGenre");

    genreDaoJdbc.insert(genre);

    Assertions.assertEquals(4, genreDaoJdbc.count());
  }

  @Test
  void getById() {
    Optional<Genre> genre = genreDaoJdbc.getById(3);

    Assertions.assertEquals("Software", genre.get().getName());
  }

  @Test
  void getAll() {
    List<Genre> genres = genreDaoJdbc.getAll();

    Assertions.assertEquals(3, genres.size());
  }

  @Test
  void deleteById() {
    Genre genre = new Genre(4L, "testGenre");
    genreDaoJdbc.insert(genre);

    genreDaoJdbc.deleteById(genre.getId());

    Assertions.assertEquals(3, genreDaoJdbc.count());
  }
}
