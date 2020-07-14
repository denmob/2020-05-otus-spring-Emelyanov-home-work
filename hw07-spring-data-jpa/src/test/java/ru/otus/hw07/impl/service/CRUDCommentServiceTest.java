package ru.otus.hw07.impl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw07.core.models.Book;
import ru.otus.hw07.core.models.Comment;
import ru.otus.hw07.core.repositories.CommentRepository;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = CRUDCommentService.class)
class CRUDCommentServiceTest {

  @MockBean
  private CommentRepository commentRepository;

  @Autowired
  private CRUDCommentService crudCommentService;

  private Comment newComment;

  @BeforeEach
  void beforeEach(){
     newComment = new Comment(0L,"comment",new Book());
  }

  @Test
  void create() {
    when(commentRepository.save(newComment)).thenReturn(newComment);

    crudCommentService.create(newComment);
    verify(commentRepository,times(1)).save(newComment);
  }

  @Test
  void read() {
    long id = 1L;
    when(commentRepository.findById(id)).thenReturn(any());

    crudCommentService.read(id);
    verify(commentRepository,times(1)).findById(id);
  }

  @Test
  void update() {
    when(commentRepository.save(newComment)).thenReturn(newComment);

    crudCommentService.update(newComment);
    verify(commentRepository,times(1)).save(newComment);
  }

  @Test
  void delete() {
    long id = 1L;
    doNothing().when(commentRepository).deleteById(id);

    crudCommentService.delete(id);
    verify(commentRepository,times(1)).deleteById(id);
  }
}
