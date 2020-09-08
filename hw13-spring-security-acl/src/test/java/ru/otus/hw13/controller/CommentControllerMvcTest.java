package ru.otus.hw13.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.hw13.model.Book;
import ru.otus.hw13.model.Comment;
import ru.otus.hw13.security.config.SpringSecurityConfig;
import ru.otus.hw13.security.error.AppAccessDeniedHandler;
import ru.otus.hw13.service.CommentServiceImpl;
import ru.otus.hw13.test.config.security.SpringSecurityAuxConfig;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {EmbeddedMongoAutoConfiguration.class, MongoAutoConfiguration.class})
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = {CommentController.class, CommentServiceImpl.class,
        SpringSecurityConfig.class, SpringSecurityAuxConfig.class, AppAccessDeniedHandler.class})
class CommentControllerMvcTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CommentServiceImpl commentService;

  @Test
  @SneakyThrows
  @WithMockUser(username = "user", password = "123", authorities = "ROLE_USER")
  @DisplayName("viewCommentPage with valid security user")
  void viewCommentPage_200() {
    Book book = Book.builder().id("123").title("title").build();
    List<Comment> comments = new ArrayList<>();
    comments.add(Comment.builder().commentary("comment1").build());

    when(commentService.readAllForBook(book.getId())).thenReturn(comments);

    mockMvc.perform(
        get("/comment/list")
            .param("id", book.getId())
            .param("title", book.getTitle()
            ))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(containsString("comment1")));
  }

  @Test
  @SneakyThrows
  @WithMockUser(username = "test", authorities = "ROLE_TEST")
  @DisplayName("viewCommentPage security user with ROLE_TEST")
  void viewCommentPage_403() {
    mockMvc.perform(get("/comment/list"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/403"));
  }

  @Test
  @SneakyThrows
  @WithMockUser(username = "admin", password = "123", authorities = "ROLE_USER")
  @DisplayName("Required parameter is not present")
  void viewCommentPage_400() {
    Book book = Book.builder().id("123").title("title").build();

    mockMvc.perform(get("/comment/list")
        .param("id", book.getId()))
        .andExpect(status().is(400));
  }

  @Test
  @SneakyThrows
  @WithMockUser(username = "user", password = "567", authorities = "ROLE_USER")
  @DisplayName("incorrect url invoke(404) viewComment")
  void viewCommentPage_404() {
    mockMvc.perform(get("/viewComment123"))
        .andExpect(status().is(404));
  }
}
