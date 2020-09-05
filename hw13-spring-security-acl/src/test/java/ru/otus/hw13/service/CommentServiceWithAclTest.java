package ru.otus.hw13.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw13.model.Book;
import ru.otus.hw13.model.Comment;
import ru.otus.hw13.repository.BookRepository;
import ru.otus.hw13.security.acls.service.MongoLookupStrategy;
import ru.otus.hw13.security.config.AclConfig;
import ru.otus.hw13.security.config.SpringSecurityConfig;
import ru.otus.hw13.security.error.AppAccessDeniedHandler;
import ru.otus.hw13.test.config.security.SpringSecurityAuxConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@Import({MongoLookupStrategy.class, AclConfig.class, CommentServiceImpl.class, SpringSecurityConfig.class, SpringSecurityAuxConfig.class, AppAccessDeniedHandler.class})
@ComponentScan({"ru.otus.hw13.config", "ru.otus.hw13.repository",  "ru.otus.hw13.security.acls.dao"})
class CommentServiceWithAclTest {

  @Autowired
  private CommentServiceImpl commentService;

  @Autowired
  private BookRepository bookRepository;

  @Test
  @WithMockUser
  @SneakyThrows
  void findAllByBookId_user() {
    Optional<Book> book = bookRepository.findAll().stream().findFirst();
    assertTrue(book.isPresent());

    List<Comment> comments = commentService.readAllForBook(book.get().getId());
    assertEquals(2, comments.size());
  }

  @Test
  @WithMockUser(username = "test", authorities = "ROLE_ALC")
  @SneakyThrows
  void findAllByBookId_test() {
    Optional<Book> book = bookRepository.findAll().stream().findFirst();
    assertTrue(book.isPresent());

    List<Comment> comments = commentService.readAllForBook(book.get().getId());
    assertEquals(1, comments.size());
  }

  @Test
  @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
  @SneakyThrows
  void findAllByBookId_admin() {
    Optional<Book> book = bookRepository.findAll().stream().findFirst();
    assertTrue(book.isPresent());

    List<Comment> comments = commentService.readAllForBook(book.get().getId());
    assertEquals(3, comments.size());
  }
}
