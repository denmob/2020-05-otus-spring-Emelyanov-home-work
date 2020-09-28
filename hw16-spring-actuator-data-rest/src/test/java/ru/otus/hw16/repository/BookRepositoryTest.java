package ru.otus.hw16.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw16.model.Book;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@ComponentScan({"ru.otus.hw16.config", "ru.otus.hw16.repository"})
class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  private List<Book> books;

  @BeforeEach
  void beforeEach() {
    books = bookRepository.findAll(Sort.by("author"));
  }

  @Test
  void findByAuthorLastNameEquals() {
    String expectLastName = books.get(0).getAuthor().getLastName();
    Optional<Book> optionalBook = bookRepository.findByAuthorLastNameEquals(expectLastName);
    assertTrue(optionalBook.isPresent());
    assertEquals(expectLastName, optionalBook.get().getAuthor().getLastName());
  }

  @Test
  @DirtiesContext
  void deleteBookByTitleEquals() {
    String expectTitle = books.get(0).getTitle();
    assertEquals(1L, bookRepository.deleteBookByTitleEquals(expectTitle));
  }

  @Test
  @DirtiesContext
  void deleteBookById() {
    String expectId = books.get(0).getId();
    assertEquals(1L, bookRepository.deleteBookById(expectId));
  }
}
