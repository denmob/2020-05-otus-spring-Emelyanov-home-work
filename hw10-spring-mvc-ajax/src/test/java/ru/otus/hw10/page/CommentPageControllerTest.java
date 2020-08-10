package ru.otus.hw10.page;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.hw10.model.Book;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureWebMvc
@AutoConfigureMockMvc
@SpringBootTest(classes = {CommentPageController.class})
class CommentPageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private Book book;

  @BeforeEach
  void beforeEach() {
    book = Book.builder().id("123").title("title").build();
  }

  @Test
  @SneakyThrows
  void commentsPage() {
    mockMvc.perform(MockMvcRequestBuilders.get("/pageCommentList/{bookId},{title}", book.getId(), book.getTitle())
        .param("id", book.getId())
        .param("title", book.getTitle()))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(containsString(book.getId())))
        .andExpect(MockMvcResultMatchers.content().string(containsString(book.getTitle())));
  }

  @Test
  @SneakyThrows
  @DisplayName("IllegalArgumentException missing pathVariable")
  void commentsPageWithMissingPathVariable() {
    Map<String, String> params = new HashMap<>();
    params.put("bookId", book.getId());
    assertThrows(IllegalArgumentException.class, () -> {
      mockMvc.perform(MockMvcRequestBuilders.get("/pageCommentList/{bookId},{title}", params));
    });
  }

  @Test
  @SneakyThrows
  @DisplayName("without pathVariable")
  void commentsPageWithoutPathVariable() {
    mockMvc.perform(MockMvcRequestBuilders.get("/pageCommentList/"))
        .andExpect(status().is(404));
  }


}
