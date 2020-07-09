package ru.otus.hw06.impl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Comment;
import ru.otus.hw06.core.repositories.CommentRepositoryJpa;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CRUDCommentService.class)
class CRUDCommentServiceTest {

  @MockBean
  private CommentRepositoryJpa commentRepositoryJpa;

  @Autowired
  private CRUDCommentService crudCommentService;


  @Test
  void create() {
    Comment comment = new Comment(0L,"comment",new Book());
    when(commentRepositoryJpa.insert(comment)).thenReturn(comment);

    crudCommentService.create(comment);
    verify(commentRepositoryJpa,times(1)).insert(comment);
  }

  @Test
  void read() {
    long id = 1L;
    when(commentRepositoryJpa.getById(id)).thenReturn(any());

    crudCommentService.read(id);
    verify(commentRepositoryJpa,times(1)).getById(id);
  }

  @Test
  void delete() {
    long id = 1L;
    when(commentRepositoryJpa.deleteById(id)).thenReturn(true);

    crudCommentService.delete(id);
    verify(commentRepositoryJpa,times(1)).deleteById(id);
  }

  @Test
  void update() {
    Comment comment = new Comment(1L,"comment",new Book());
    when(commentRepositoryJpa.insert(comment)).thenReturn(comment);

    crudCommentService.update(comment);
    verify(commentRepositoryJpa,times(1)).insert(comment);
  }
}
