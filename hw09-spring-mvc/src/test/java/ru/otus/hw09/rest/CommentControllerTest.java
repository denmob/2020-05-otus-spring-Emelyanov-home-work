package ru.otus.hw09.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.otus.hw09.model.Book;
import ru.otus.hw09.service.CommentServiceImpl;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CommentServiceImpl.class, CommentController.class})
class CommentControllerTest {

  @MockBean
  private CommentServiceImpl commentService;

  @Autowired
  private CommentController commentController;

  @Test
  void viewCommentPage() {
    Book book = Book.builder().id("123").title("title").build();
    Model model = new ConcurrentModel();

    when(commentService.readAllForBook(book.getId())).thenReturn(new ArrayList<>());

    commentController.viewCommentPage(book.getId(), book.getTitle(), model);

    assertEquals(book.getTitle(), model.getAttribute("title"));

    verify(commentService, times(1)).readAllForBook(book.getId());
  }
}
