package ru.otus.hw06.impl.repositories;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.repositories.CommentRepositoryJpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.DateUtil.now;

@Slf4j
@DataJpaTest
@Sql("classpath:data-test.sql")
@Import({BookRepositoryJpaImpl.class})
class BookRepositoryJpaImplTest {

  @Autowired
  private BookRepositoryJpaImpl bookRepositoryJpa;

  @Autowired
  private TestEntityManager testEntityManager;

  private SessionFactory sessionFactory;

  @BeforeEach
  void beforeEach() {
    testEntityManager.clear();
    sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
    sessionFactory.getStatistics().setStatisticsEnabled(true);
    sessionFactory.getStatistics().clear();
  }

  @Test
  void count() {
    Assertions.assertEquals(3L, bookRepositoryJpa.count());

    assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
  }

  @Test
  void insertNewBook() {
    Author author = Author.builder().firstName("FirstName").lastName("LastName").birthday(now()).build();
    Genre genre = Genre.builder().name("new genre").build();

    Book bookExpect = Book.builder().title("title").author(author).genre(genre).date(now()).build();

    bookRepositoryJpa.insert(bookExpect);

    Book bookActual = testEntityManager.find(Book.class, 4L);

    assertThat(bookExpect).isEqualTo(bookActual);
  }

  @Test
  void insertDetachBook() {
    Author author = Author.builder().firstName("FirstName").lastName("LastName").birthday(now()).build();
    Genre genre = Genre.builder().name("new genre").build();

    Book bookExpect = Book.builder().id(1L).title("title").author(author).genre(genre).date(now()).build();

    bookExpect = bookRepositoryJpa.insert(bookExpect);

    Book bookActual = testEntityManager.find(Book.class, 1L);

    assertThat(bookExpect).isEqualTo(bookActual);
  }

  @Test
  void getById() {
    Assertions.assertEquals(testEntityManager.find(Book.class, 3L), bookRepositoryJpa.getById(3L).get());
  }

  @Test
  void getAll() {
    List<Book> books = bookRepositoryJpa.getAll();
    assertThat(books.size()).isEqualTo(3);
    assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
  }

  @Test
  void deleteById() {
    assertThat(bookRepositoryJpa.deleteById(3L)).isEqualTo(true);
    Assertions.assertEquals(2L, bookRepositoryJpa.count());
  }

}
