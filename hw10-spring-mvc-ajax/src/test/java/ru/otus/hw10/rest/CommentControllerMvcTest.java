package ru.otus.hw10.rest;

import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw10.model.Book;
import ru.otus.hw10.model.Comment;
import ru.otus.hw10.service.CommentServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class})
@SpringBootTest(classes = {CommentServiceImpl.class, CommentController.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentControllerMvcTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CommentServiceImpl commentService;

  private Book book;

  @BeforeEach
  void beforeEach(){
    book = Book.builder().id("123").title("title").build();
  }

  @Test
  @SneakyThrows
  void getComments() {
    List<Comment> comments = new ArrayList<>();
    Comment expectComment = Comment.builder().commentary("comment1").build();
    comments.add(expectComment);

    when(commentService.readAllForBook(book.getId())).thenReturn(comments);

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/comments/{bookId}", book.getId())).andExpect(status().isOk()).andReturn();
    MockHttpServletResponse response = mvcResult.getResponse();
    String actualCommentary = JsonPath.parse(response.getContentAsString()).read("$[0].commentary");
    assertEquals(expectComment.getCommentary(),actualCommentary);

    verify(commentService,times(1)).readAllForBook(book.getId());
  }

  @Test
  @SneakyThrows
  @DisplayName("PathVariable is empty")
  void getCommentsWithEmptyVariable() {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/comments/{bookId}",""))
        .andExpect(status().is(404));
  }

  @Test
  @SneakyThrows
  @DisplayName("PathVariable parameter is not present")
  void getCommentsWithoutVariable() {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/comments/"))
        .andExpect(status().is(404));
  }
}
