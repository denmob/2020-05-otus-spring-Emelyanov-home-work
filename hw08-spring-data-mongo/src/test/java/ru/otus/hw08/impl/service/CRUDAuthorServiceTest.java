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
  private Author oldAuthor;

  @BeforeEach
  void beforeEach(){
    newAuthor = new Author("0","new FirstName","new LastName",now());
    oldAuthor = new Author("1","old FirstName","old LastName",now());
  }

  @Test
  void create() {
    when(authorRepository.save(newAuthor)).thenReturn(newAuthor);

    crudAuthorService.create(newAuthor);
    verify(authorRepository,times(1)).save(newAuthor);
  }

  @Test
  void read() {
    when(authorRepository.findByLastNameEquals(oldAuthor.getLastName())).thenReturn(any());

    crudAuthorService.readLastNameEquals(oldAuthor.getLastName());
    verify(authorRepository,times(1)).findByLastNameEquals(oldAuthor.getLastName());
  }

  @Test
  void delete() {
    when(authorRepository.deleteAuthorById(newAuthor.getId())).thenReturn(1L);

    crudAuthorService.delete(newAuthor.getId());
    verify(authorRepository,times(1)).deleteAuthorById(newAuthor.getId());
  }

  @Test
  void update() {
    when(authorRepository.save(newAuthor)).thenReturn(newAuthor);

    crudAuthorService.update(newAuthor);
    verify(authorRepository,times(1)).save(newAuthor);
  }
}
