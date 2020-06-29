package ru.otus.hw05.impl.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw05.core.domain.Book;

import java.text.SimpleDateFormat;
import java.util.List;

@JdbcTest
@Import({BookDaoJdbc.class})
class BookDaoJdbcTest {

  @Autowired
  private BookDaoJdbc bookDaoJdbc;

  @Test
  void count() {
    Assertions.assertEquals(3, bookDaoJdbc.count());
  }

  @SneakyThrows
  @Test
  void insert() {
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    Book book = new Book(4L, 1L, 1L, "TestBook", sf.parse("2020-06-29"));

    bookDaoJdbc.insert(book);

    Assertions.assertEquals(4, bookDaoJdbc.count());
    Assertions.assertEquals(book, bookDaoJdbc.getById(book.getId()));
  }

  @Test
  void getById() {
    Book book = bookDaoJdbc.getById(1L);

    Assertions.assertEquals("Pragmatic Unit Testing in Java 8 with JUnit", book.getTitle());
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
