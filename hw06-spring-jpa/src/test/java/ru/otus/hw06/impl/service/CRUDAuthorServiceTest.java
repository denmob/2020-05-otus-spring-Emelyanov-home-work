package ru.otus.hw06.impl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.repositories.AuthorRepositoryJpa;

import static org.assertj.core.util.DateUtil.now;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CRUDAuthorService.class)
class CRUDAuthorServiceTest {

  @MockBean
  private AuthorRepositoryJpa authorRepositoryJpa;

  @Autowired
  private CRUDAuthorService crudAuthorService;

  @Test
  void create() {
    Author author = new Author(0L,"FirstName","LastName",now());
    when(authorRepositoryJpa.insert(author)).thenReturn(author);

    crudAuthorService.create(author);
    verify(authorRepositoryJpa,times(1)).insert(author);
  }

  @Test
  void read() {
    long id = 1L;
    when(authorRepositoryJpa.getById(id)).thenReturn(any());

    crudAuthorService.read(id);
    verify(authorRepositoryJpa,times(1)).getById(id);
  }

  @Test
  void delete() {
    long id = 1L;
    when(authorRepositoryJpa.deleteById(id)).thenReturn(true);

    crudAuthorService.delete(id);
    verify(authorRepositoryJpa,times(1)).deleteById(id);
  }

  @Test
  void update() {
    Author author = new Author(3L,"FirstName","LastName",now());
    when(authorRepositoryJpa.insert(author)).thenReturn(author);

    crudAuthorService.update(author);
    verify(authorRepositoryJpa,times(1)).insert(author);
  }
}
