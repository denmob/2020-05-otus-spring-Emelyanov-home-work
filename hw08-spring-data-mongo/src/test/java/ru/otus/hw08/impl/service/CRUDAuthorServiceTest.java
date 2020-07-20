package ru.otus.hw08.impl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw08.core.models.Author;
import ru.otus.hw08.core.repositories.AuthorRepository;

import static org.assertj.core.util.DateUtil.now;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CRUDAuthorService.class)
class CRUDAuthorServiceTest {

  @MockBean
  private AuthorRepository authorRepository;

  @Autowired
  private CRUDAuthorService crudAuthorService;

  private Author newAuthor;

  @BeforeEach
  void beforeEach(){
    newAuthor = new Author("1","FirstName","LastName",now());
  }

  @Test
  void create() {
    when(authorRepository.save(newAuthor)).thenReturn(newAuthor);

    crudAuthorService.create(newAuthor);
    verify(authorRepository,times(1)).save(newAuthor);
  }

  @Test
  void read() {
    String id = "1";
    when(authorRepository.findById(id)).thenReturn(any());

    crudAuthorService.read(id);
    verify(authorRepository,times(1)).findById(id);
  }

  @Test
  void delete() {
    doNothing().when(authorRepository).deleteById(newAuthor.getId());

    crudAuthorService.delete(newAuthor.getId());
    verify(authorRepository,times(1)).deleteById(newAuthor.getId());
  }

  @Test
  void update() {
    when(authorRepository.save(newAuthor)).thenReturn(newAuthor);

    crudAuthorService.update(newAuthor);
    verify(authorRepository,times(1)).save(newAuthor);
  }
}
