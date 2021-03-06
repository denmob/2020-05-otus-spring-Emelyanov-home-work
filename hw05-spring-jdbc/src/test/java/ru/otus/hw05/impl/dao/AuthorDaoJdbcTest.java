package ru.otus.hw05.impl.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.hw05.core.domain.Author;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import({AuthorDaoJdbc.class})
class AuthorDaoJdbcTest {

  @Autowired
  private AuthorDaoJdbc authorDaoJdbc;

  @Test
  void count() {
    Assertions.assertEquals(3, authorDaoJdbc.count());
  }

  @SneakyThrows
  @Test
  void insert() {
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    Author author = new Author(4L,"Evgeny","Borisov",sf.parse("1978-10-03"));

    authorDaoJdbc.insert(author);

    Assertions.assertEquals(4, authorDaoJdbc.count());
  }

  @Test
  void getById() {
    Author author = authorDaoJdbc.getById(2L);

    Assertions.assertEquals("Joshua",author.getFirstName());
  }

  @Test
  void getAll() {
    List<Author> authors = authorDaoJdbc.getAll();

    Assertions.assertEquals(3, authors.size());
  }

  @Test
  @DisplayName("delete record with constrain")
  void deleteByIdThrow() {
    assertThrows(DataIntegrityViolationException.class, () -> {
      authorDaoJdbc.deleteById(3L);
    });
  }

  @SneakyThrows
  @Test
  void deleteById() {
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    Author author = new Author(4L,"Evgeny","Borisov",sf.parse("1978-10-03"));
    Assertions.assertEquals(3, authorDaoJdbc.count());

    authorDaoJdbc.insert(author);
    Assertions.assertEquals(4, authorDaoJdbc.count());

    authorDaoJdbc.deleteById(author.getId());
    Assertions.assertEquals(3, authorDaoJdbc.count());
  }

}
