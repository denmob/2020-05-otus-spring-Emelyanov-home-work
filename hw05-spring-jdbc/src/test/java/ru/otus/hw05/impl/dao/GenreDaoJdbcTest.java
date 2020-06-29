package ru.otus.hw05.impl.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw05.core.domain.Genre;

import java.util.List;

@JdbcTest
@Import({GenreDaoJdbc.class})
class GenreDaoJdbcTest {

  @Autowired
  private GenreDaoJdbc genreDaoJdbc;

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
    Genre genre = genreDaoJdbc.getById(3);

    Assertions.assertEquals("Software", genre.getName());
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
