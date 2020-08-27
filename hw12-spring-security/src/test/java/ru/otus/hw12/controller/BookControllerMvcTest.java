package ru.otus.hw12.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.otus.hw12.model.Author;
import ru.otus.hw12.model.Book;
import ru.otus.hw12.model.Genre;
import ru.otus.hw12.security.config.SpringSecurityConfig;
import ru.otus.hw12.security.error.AppAccessDeniedHandler;
import ru.otus.hw12.service.*;
import ru.otus.hw12.test.config.security.UserDetailsServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.util.DateUtil.now;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({BookController.class,
    BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class, CommentServiceImpl.class,
    SpringSecurityConfig.class, UserDetailsServiceImpl.class, AppAccessDeniedHandler.class})
@WebMvcTest(controllers = BookController.class)
class BookControllerMvcTest {

  @MockBean
  private BookServiceImpl bookService;
  @MockBean
  private AuthorServiceImpl authorService;
  @MockBean
  private GenreServiceImpl genreService;
  @MockBean
  private CommentServiceImpl commentService;

  @Autowired
  private MockMvc mockMvc;

  private Book book1;
  private Book book2;

  @BeforeEach
  void beforeEach() {
    Author newAuthor = new Author("0", "FirstName", "LastName", now());
    Genre newGenre = new Genre("0", "newGenre");
    book1 = new Book("0", "Title new", now(), newAuthor, newGenre);
    book2 = new Book("1", "Title old", now(), newAuthor, newGenre);
  }

  @Test
  @SneakyThrows
  @WithMockUser(username = "user", password = "123", authorities = {"ROLE_USER"})
  void listBookPage() {
    int countBook = 3;
    List<Book> list = new ArrayList<>();
    list.add(book1);
    list.add(book2);
    Page<Book> books = new PageImpl<>(list);
    when(bookService.getLastAddedBooks(countBook)).thenReturn(books);

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/listBook").param("countBook", String.valueOf(countBook)))
        .andExpect(status().is(200))
        .andExpect(MockMvcResultMatchers.content().string(containsString("<title>List book</title>")))
        .andReturn();

    assertEquals("text/html;charset=UTF-8", mvcResult.getResponse().getContentType());
  }

  @Test
  void createBookPage() {
  }

  @Test
  void editBookPage() {
  }

  @Test
  void saveBook() {
  }

  @Test
  void deleteBook() {
  }

  @SpringBootConfiguration
  public static class StopWebMvcScan {
  }
}
