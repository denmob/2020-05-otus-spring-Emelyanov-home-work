package ru.otus.hw10.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import ru.otus.hw10.model.Book;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@ComponentScan({"ru.otus.hw10.test.config.mongock", "ru.otus.hw10.repository"})
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
  @DisplayName("expectTitle with findByTitleContainsOrDateEquals")
  void findByTitleContainsOrDateEqualsWithTile() {
    String expectTitle = books.get(0).getTitle();
    List<Book> books = bookRepository.findByTitleContainsOrDateEquals(expectTitle, null);
    assertEquals(1, books.size());
    assertEquals(expectTitle, books.get(0).getTitle());
  }

  @Test
  @DisplayName("expectTitle with findByTitleContainsOrDateEquals")
  void findByTitleContainsOrDateEqualsWithDate() {
    Date expectDate = books.get(0).getDate();
    List<Book> books = bookRepository.findByTitleContainsOrDateEquals("1", expectDate);
    assertEquals(1, books.size());
    assertEquals(expectDate, books.get(0).getDate());
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
