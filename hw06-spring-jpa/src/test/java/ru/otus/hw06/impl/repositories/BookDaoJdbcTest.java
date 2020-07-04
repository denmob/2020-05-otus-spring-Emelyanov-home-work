package ru.otus.hw06.impl.repositories;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw06.core.models.Book;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@Import({BookRepositoryJpaImpl.class})
class BookDaoJdbcTest {

  @Autowired
  private BookRepositoryJpaImpl bookDaoJdbc;

  @Test
  void count() {
    Assertions.assertEquals(3, bookDaoJdbc.count());
  }

  @SneakyThrows
  @Test
  void insert() {
//    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//    Book book = new Book(4L, 1L, 1L, "TestBook", sf.parse("2020-06-29"));
//
//    bookDaoJdbc.insert(book);
//
//    Assertions.assertEquals(4, bookDaoJdbc.count());
//    Assertions.assertEquals(book, bookDaoJdbc.getById(book.getId()));
  }

  @Test
  void getById() {
    Optional<Book> book = bookDaoJdbc.getById(1L);

    Assertions.assertEquals("Pragmatic Unit Testing in Java 8 with JUnit", book.get().getTitle());
  }

  @Test
  void getAll() {
    List<Book> books = bookDaoJdbc.getAll();

    Assertions.assertEquals(3, books.size());
  }

  @Test
  void deleteById() {
    bookDaoJdbc.deleteById(3L);

    Assertions.assertEquals(2, bookDaoJdbc.count());
  }
}
