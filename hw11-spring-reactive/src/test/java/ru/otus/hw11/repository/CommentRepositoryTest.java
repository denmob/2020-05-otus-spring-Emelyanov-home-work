package ru.otus.hw11.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;
import ru.otus.hw11.model.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@ComponentScan({"ru.otus.hw11.test.config.mongock", "ru.otus.hw11.repository"})
class CommentRepositoryTest {

  @Autowired
  private CommentRepository repository;

  @Autowired
  private BookRepository bookRepository;

  @Test
  void findAllByBookId() {
    Book book = bookRepository.findAll().blockLast();
    assertNotNull(book);
    log.info(book.toString());

    StepVerifier
        .create(repository.findAllByBookId(book.getId()))
        .assertNext(comment -> {
          assertEquals("addComments03", comment.getCommentary());
        })
        .expectNextCount(0)
        .expectComplete()
        .verify();
  }
}
