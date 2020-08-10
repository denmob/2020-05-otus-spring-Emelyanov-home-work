package ru.otus.hw10.rest;

import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw10.model.Book;
import ru.otus.hw10.model.Comment;
import ru.otus.hw10.service.CommentServiceImpl;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class})
@SpringBootTest(classes = {CommentServiceImpl.class, CommentController.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentControllerRestTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @MockBean
  private CommentServiceImpl commentService;

  private static HttpEntity<?> httpEntity;

  private Book book;

  @BeforeAll
  static void beforeAll() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    httpEntity = new HttpEntity<>(httpHeaders);
  }

  @BeforeEach
  void beforeEach() {
    book = Book.builder().id("123").title("title").build();
  }

  @Test
  @SneakyThrows
  void getComments() {
    Map<String, String> params = new HashMap<>();
    params.put("bookId", book.getId());

    List<Comment> comments = new ArrayList<>();
    Comment expectComment = Comment.builder().commentary("comment1").build();
    comments.add(expectComment);

    when(commentService.readAllForBook(book.getId())).thenReturn(comments);

    ResponseEntity<String> responseEntity = testRestTemplate.exchange("/api/comment/list/{bookId}", HttpMethod.GET, httpEntity, String.class, params);
    String actualCommentary = JsonPath.parse(responseEntity.getBody()).read("$[0].commentary");
    assertEquals(expectComment.getCommentary(), actualCommentary);

    verify(commentService, times(1)).readAllForBook(book.getId());
  }


}
