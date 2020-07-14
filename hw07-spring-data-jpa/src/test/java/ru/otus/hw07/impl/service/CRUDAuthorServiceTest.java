package ru.otus.hw07.impl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw07.core.models.Author;
import ru.otus.hw07.core.repositories.AuthorRepository;

import java.util.Optional;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CRUDAuthorService.class)
class CRUDAuthorServiceTest {

  @MockBean
  private AuthorRepository authorRepository;

  @Autowired
  private CRUDAuthorService crudAuthorService;

  @Test
  void create() {
    Author author = new Author(1L,"FirstName","LastName",now());
    when(authorRepository.save(author)).thenReturn(author);

    crudAuthorService.create(author);
    verify(authorRepository,times(1)).save(author);
  }

  @Test
  void read() {
    long id = 1L;
    when(authorRepository.findById(id)).thenReturn(any());

    crudAuthorService.read(id);
    verify(authorRepository,times(1)).findById(id);
  }

  @Test
  void delete() {
    Author author = new Author(1L,"FirstName","LastName",now());
    doNothing().when(authorRepository).deleteById(author.getId());

    crudAuthorService.delete(author.getId());
    verify(authorRepository,times(1)).deleteById(author.getId());
  }

  @Test
  void update() {
    Author author = new Author(3L,"FirstName","LastName",now());
    when(authorRepository.save(author)).thenReturn(author);

    crudAuthorService.update(author);
    verify(authorRepository,times(1)).save(author);
  }
}
