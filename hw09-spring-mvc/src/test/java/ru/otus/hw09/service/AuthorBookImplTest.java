package ru.otus.hw09.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw09.model.Author;
import ru.otus.hw09.repository.AuthorRepository;

import static org.assertj.core.util.DateUtil.now;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AuthorServiceImpl.class)
class AuthorBookImplTest {

  @MockBean
  private AuthorRepository authorRepository;

  @Autowired
  private AuthorServiceImpl authorService;

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

    authorService.save(newAuthor);
    verify(authorRepository,times(1)).save(newAuthor);
  }

  @Test
  void read() {
    when(authorRepository.findByLastNameEquals(oldAuthor.getLastName())).thenReturn(any());

    authorService.findByLastNameEquals(oldAuthor.getLastName());
    verify(authorRepository,times(1)).findByLastNameEquals(oldAuthor.getLastName());
  }

  @Test
  void delete() {
    when(authorRepository.deleteAuthorById(newAuthor.getId())).thenReturn(1L);

    authorService.deleteAuthorById(newAuthor.getId());
    verify(authorRepository,times(1)).deleteAuthorById(newAuthor.getId());
  }

  @Test
  void update() {
    when(authorRepository.save(newAuthor)).thenReturn(newAuthor);

    authorService.save(newAuthor);
    verify(authorRepository,times(1)).save(newAuthor);
  }
}
