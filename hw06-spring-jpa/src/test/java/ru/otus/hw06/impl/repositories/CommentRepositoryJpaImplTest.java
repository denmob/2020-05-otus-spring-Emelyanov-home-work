package ru.otus.hw06.impl.repositories;

import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("classpath:data-test.sql")
@Import(CommentRepositoryJpaImpl.class)
class CommentRepositoryJpaImplTest {

  @Autowired
  private CommentRepositoryJpaImpl commentRepositoryJpa;

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
    Assertions.assertEquals(2L, commentRepositoryJpa.count());

    assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
  }

  @Test
  void insertNewComment() {
    Comment commentExpected = new Comment(0L,"testComment",new Book());
    commentExpected = commentRepositoryJpa.insert(commentExpected);
    Comment commentActual = commentRepositoryJpa.getById(commentExpected.getId()).get();
    Assertions.assertEquals(commentExpected,commentActual);
  }

  @Test
  void updateComment() {
    Comment commentExpected = new Comment(0L,"testComment",new Book());
    commentExpected = commentRepositoryJpa.insert(commentExpected);
    commentExpected.setCommentary("testComment 2");
    commentExpected = commentRepositoryJpa.insert(commentExpected);

    Comment commentActual = commentRepositoryJpa.getById(commentExpected.getId()).get();

    Assertions.assertEquals(commentExpected.getCommentary(),commentActual.getCommentary());
  }

  @Test
  void getById() {
    Comment commentExpected = new Comment(0L,"testComment",new Book());
    commentRepositoryJpa.insert(commentExpected);
    Comment commentActual = commentRepositoryJpa.getById(commentExpected.getId()).get();
    Assertions.assertEquals(commentExpected,commentActual);
  }

  @Test
  void getAll() {
    List<Comment> comments = commentRepositoryJpa.getAll();
    Assertions.assertEquals(2,comments.size());
  }


  @Test
  void getAllByBookId() {
    List<Comment> comments = commentRepositoryJpa.getAllByBookId(1L);
    Assertions.assertEquals(2,comments.size());
  }
}
