package ru.otus.hw16.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw16.model.Author;
import ru.otus.hw16.model.Book;
import ru.otus.hw16.model.Comment;
import ru.otus.hw16.model.Genre;
import ru.otus.hw16.rest.dto.CommentDto;
import ru.otus.hw16.service.CommentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest( classes = {CommentController.class,CommentServiceImpl.class})
class CommentControllerUnitTest {

  @Autowired
  private CommentController commentController;

  @MockBean
  private CommentServiceImpl commentService;

  private Book oldBook;

  @BeforeEach
  void beforeEach() {
    Author newAuthor = new Author("0", "FirstName", "LastName", now());
    Genre newGenre = new Genre("0", "newGenre");
    oldBook = new Book("1", "Title old", now(), newAuthor, newGenre);
  }

  @Test
  void getComments() {
    List<Comment> comments = new ArrayList<>();
    comments.add(new Comment());
    comments.add(new Comment());

    when(commentService.readAllForBook(oldBook.getId())).thenReturn(comments);

    List<CommentDto> actual = commentController.getComments(oldBook.getId());

    List<CommentDto> expect = comments.stream().map(CommentDto::toDto).collect(Collectors.toList());
    assertEquals(expect, actual);
  }
}
