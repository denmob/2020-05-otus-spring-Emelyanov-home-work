package ru.otus.hw10.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw10.model.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@ComponentScan({"ru.otus.hw10.test.config.mongock", "ru.otus.hw10.repository"})
class CommentRepositoryTest {

  @Autowired
  private CommentRepository commentRepository;

  private List<Comment> comments;

  @BeforeEach
  void beforeEach() {
    comments = commentRepository.findAll(Sort.by("bookId"));
  }

  @Test
  void findAllByBookId() {
    String bookId = comments.get(0).getBookId();
    assertEquals(2, commentRepository.findAllByBookId(bookId).size());
  }

  @Test
  @DirtiesContext
  void deleteCommentAllByBookId() {
    String bookId = comments.get(0).getBookId();
    commentRepository.deleteCommentAllByBookId(bookId);
    assertEquals(0, commentRepository.findAllByBookId(bookId).size());
  }

  @Test
  @DirtiesContext
  void deleteCommentByCommentaryContains() {
    assertFalse(commentRepository.findCommentByCommentaryContains("03").isEmpty());
    assertEquals(1, commentRepository.deleteCommentByCommentaryContains("03"));
    assertTrue(commentRepository.findCommentByCommentaryContains("03").isEmpty());
  }

  @Test
  @DirtiesContext
  void deleteCommentByIdExistId() {
    assertEquals(1, commentRepository.deleteCommentById(comments.get(0).getId()));
  }

  @Test
  @DirtiesContext
  void deleteCommentByIdNonExistId() {
    assertEquals(0, commentRepository.deleteCommentById("78987978"));
  }

  @Test
  void findCommentByCommentaryContains() {
    assertFalse(commentRepository.findCommentByCommentaryContains("03").isEmpty());
  }
}
