package ru.otus.hw08.impl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw08.core.models.Book;
import ru.otus.hw08.core.models.Comment;
import ru.otus.hw08.core.repositories.CommentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = CRUDCommentService.class)
class CRUDCommentServiceTest {

  @MockBean
  private CommentRepository commentRepository;

  @Autowired
  private CRUDCommentService crudCommentService;

  private Comment newComment;
  private Comment oldComment;

  @BeforeEach
  void beforeEach() {
    newComment = new Comment("0", "new comment", "1");
    oldComment = new Comment("1", "old comment", "2");
  }

  @Test
  void create() {
    when(commentRepository.save(newComment)).thenReturn(newComment);

    crudCommentService.create(newComment);
    verify(commentRepository, times(1)).save(newComment);
  }

  @Test
  void read() {
    when(commentRepository.findById(oldComment.getId())).thenReturn(Optional.ofNullable(oldComment));

    crudCommentService.read(oldComment.getId());
    verify(commentRepository, times(1)).findById(oldComment.getId());
  }

  @Test
  void update() {
    when(commentRepository.save(newComment)).thenReturn(newComment);

    crudCommentService.update(newComment);
    verify(commentRepository, times(1)).save(newComment);
  }

  @Test
  void delete() {
    doNothing().when(commentRepository).deleteById(oldComment.getId());

    crudCommentService.delete(oldComment.getId());
    verify(commentRepository, times(1)).deleteById(oldComment.getId());
  }

  @Test
  void readAllForBook() {
    List<Comment> comments = Collections.singletonList(oldComment);
    when(commentRepository.findAllByBookId(oldComment.getBookId())).thenReturn(comments);

    Assertions.assertEquals(comments, crudCommentService.readAllForBook(oldComment.getBookId()));
  }
}
