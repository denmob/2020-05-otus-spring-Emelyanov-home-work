package ru.otus.hw06.impl.repositories;

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

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("classpath:data-test.sql")
@Import(AuthorRepositoryJpaImpl.class)
class AuthorRepositoryJpaImplTest {

  @Autowired
  private AuthorRepositoryJpaImpl authorRepositoryJpa;

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
    Assertions.assertEquals(3L, authorRepositoryJpa.count());

    assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
  }

  @Test
  @DisplayName("insert new author")
  void insertNewAuthor() {
    Author authorExpect = new Author();
    authorExpect.setFirstName("FirstName");
    authorExpect.setLastName("LastName");
    authorRepositoryJpa.insert(authorExpect);

    Author authorActual = testEntityManager.find(Author.class, 4L);

    assertThat(authorExpect).isEqualTo(authorActual);
  }

  @Test
  @DisplayName("edit author")
  void insertDetachAuthor() {
    Author authorExpect = new Author();
    authorExpect.setId(1L);
    authorExpect.setFirstName("FirstName");
    authorExpect.setLastName("LastName");
    authorRepositoryJpa.insert(authorExpect);

    Author authorActual = testEntityManager.find(Author.class, 1L);

    assertThat(authorExpect).isEqualTo(authorActual);
  }

  @Test
  void getById() {
    Assertions.assertEquals(testEntityManager.find(Author.class, 3L), authorRepositoryJpa.getById(3L).get());
  }

  @Test
  void getAll() {
    assertThat(authorRepositoryJpa.getAll().size()).isEqualTo(3);
    assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
  }

  @Test
  void deleteById() {
    assertThat(authorRepositoryJpa.deleteById(3L)).isEqualTo(true);
    Assertions.assertEquals(2L, authorRepositoryJpa.count());
  }
}
