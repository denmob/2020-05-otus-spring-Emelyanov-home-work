package ru.otus.hw11.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;
import ru.otus.hw11.model.Book;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@ComponentScan({"ru.otus.hw11.test.config.mongock", "ru.otus.hw11.repository"})
class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  private Book lastBook;

  @BeforeEach
  void beforeEach() {
    lastBook = bookRepository.findAll().blockLast();
  }

  @Test
  void findAll() {
    StepVerifier
        .create(bookRepository.findAll().sort(Comparator.comparing(Book::getId).reversed()).skip(1).take(1))
        .expectNextMatches(book -> book.getTitle().equals("Effective Java(test)"))
        .expectNextCount(0)
        .expectComplete()
        .verify();
  }

  @Test
  @DirtiesContext
  @DisplayName("deleteBookById with exist id")
  void deleteBookById() {
    StepVerifier
        .create(bookRepository.deleteBookById(lastBook.getId()))
        .expectNext(1L)
        .expectComplete()
        .verify();

    StepVerifier
        .create(bookRepository.findAll())
        .expectNextCount(2)
        .expectComplete()
        .verify();
  }

  @Test
  @DisplayName("deleteBookById with illegal id")
  void deleteBookByIllegalId() {
    StepVerifier
        .create(bookRepository.deleteBookById("0"))
        .expectNext(0L)
        .expectComplete()
        .verify();
  }

  @Test
  @DisplayName("getBookById with exist id")
  void getBookById() {
    StepVerifier
        .create(bookRepository.findById(lastBook.getId()))
        .expectNextMatches(book -> book.getTitle().equals("Java Core Fundamentals(test)"))
        .expectNextCount(0)
        .expectComplete()
        .verify();
  }

  @Test
  @DisplayName("getBookById with illegal id")
  void getBookByIllegalId() {
    StepVerifier
        .create(bookRepository.findById("89"))
        .expectNextCount(0)
        .expectComplete()
        .verify();
  }

  @Test
  @DirtiesContext
  void postBook() {
    Book bookExpect = Book.builder().title("new book").build();

    StepVerifier
        .create(bookRepository.save(bookExpect))
        .assertNext(book -> assertNotNull(book.getId()))
        .expectComplete()
        .verify();

    StepVerifier
        .create(bookRepository.findAll())
        .expectNextCount(4)
        .expectComplete()
        .verify();
  }

  @Test
  @DirtiesContext
  void putBook() {
    lastBook.setTitle("change title last book");

    StepVerifier
        .create(bookRepository.save(lastBook))
        .assertNext(book -> assertEquals(lastBook.getTitle(), book.getTitle()))
        .expectComplete()
        .verify();
  }
}
