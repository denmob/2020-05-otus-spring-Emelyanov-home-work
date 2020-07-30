package ru.otus.hw09.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.otus.hw09.model.Book;
import ru.otus.hw09.model.Comment;
import ru.otus.hw09.service.CommentService;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw09.service.CommentServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {CommentServiceImpl.class, CommentController.class})
@AutoConfigureWebMvc
@AutoConfigureMockMvc
class CommentControllerMvcTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CommentServiceImpl commentService;

  @Test
  @SneakyThrows
  @DisplayName("correct invoke(200) viewComment")
  void viewCommentPage() {
    Book book = Book.builder().id("123").title("title").build();
    List<Comment> comments = new ArrayList<>();
    comments.add(Comment.builder().commentary("comment1").build());

    when(commentService.readAllForBook(book.getId())).thenReturn(comments);

    mockMvc.perform(MockMvcRequestBuilders.get("/viewComment")
        .param("id", book.getId())
        .param("title", book.getTitle()))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(containsString("comment1")));
  }

  @Test
  @SneakyThrows
  @DisplayName("Required parameter is not present")
  void viewCommentPage400() {
    Book book = Book.builder().id("123").title("title").build();
    mockMvc.perform(MockMvcRequestBuilders.get("/viewComment")
        .param("id", book.getId()))
        .andExpect(status().is(400));
  }

  @Test
  @SneakyThrows
  @DisplayName("incorrect url invoke(404) viewComment")
  void viewCommentPage404() {
    mockMvc.perform(MockMvcRequestBuilders.get("/viewComment123"))
        .andExpect(status().is(404));
  }
}
