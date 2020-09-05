package ru.otus.hw13.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.hw13.model.Author;
import ru.otus.hw13.model.Book;
import ru.otus.hw13.model.Genre;
import ru.otus.hw13.security.config.SpringSecurityConfig;
import ru.otus.hw13.security.error.AppAccessDeniedHandler;
import ru.otus.hw13.service.AuthorServiceImpl;
import ru.otus.hw13.service.BookServiceImpl;
import ru.otus.hw13.service.CommentServiceImpl;
import ru.otus.hw13.service.GenreServiceImpl;
import ru.otus.hw13.test.config.security.SpringSecurityAuxConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.util.DateUtil.now;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({BookController.class, BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class, CommentServiceImpl.class,
    SpringSecurityConfig.class, SpringSecurityAuxConfig.class, AppAccessDeniedHandler.class})
@WebMvcTest(controllers = BookController.class, useDefaultFilters = false)
class BookControllerMvcAclTest {

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
  private List<Author> authors;
  private List<Genre> genres;

  @BeforeEach
  void beforeEach() {
    Author author = new Author("0", "FirstName", "LastName", now());
    Genre genre = new Genre("0", "newGenre");
    book1 = new Book("77", "Title new", now(), author, genre);
    book2 = new Book("88", "Title old", now(), author, genre);

    authors = new ArrayList<>();
    authors.add(book1.getAuthor());
    genres = new ArrayList<>();
    genres.add(book1.getGenre());
  }

  @Test
  @SneakyThrows
  @WithMockUser(username = "user", authorities = "ROLE_USER")
  void editBookPage_acl_user() {

    when(bookService.readBookById(book1.getId())).thenReturn(Optional.ofNullable(book2));
    when(authorService.findAll()).thenReturn(authors);
    when(genreService.findAll()).thenReturn(genres);

    MvcResult mvcResult = mockMvc.perform(get("/book/edit").param("id", book1.getId()))
        .andExpect(status().is(200))
        .andExpect(MockMvcResultMatchers.content().string(containsString("<title>Edit book</title>")))
        .andReturn();

    assertEquals("text/html;charset=UTF-8", mvcResult.getResponse().getContentType());
  }

  @Test
  @SneakyThrows
  @WithMockUser(username = "test", authorities = "ROLE_ALC")
  void editBookPage_acl_test() {

    when(bookService.readBookById(book1.getId())).thenReturn(Optional.ofNullable(book2));
    when(authorService.findAll()).thenReturn(authors);
    when(genreService.findAll()).thenReturn(genres);

    mockMvc.perform(get("/book/edit").param("id", book1.getId()))
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/403"));
  }

  @Test
  @SneakyThrows
  @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
  void saveBook_acl_admin() {
    when(bookService.save(book1)).thenReturn(book1);

    mockMvc.perform(post("/book/save").requestAttr("book", book1))
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/book/list"));
  }

  @Test
  @SneakyThrows
  @WithMockUser(username = "test", authorities = "ROLE_ACL")
  void saveBook_acl_test() {

    mockMvc.perform(post("/book/save").requestAttr("book", book1))
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/403"));
  }
}
