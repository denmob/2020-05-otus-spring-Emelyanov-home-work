package ru.otus.hw12.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw12.model.Author;
import ru.otus.hw12.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AuthorServiceImpl.class)
class AuthorServiceImplTest {

  @MockBean
  private AuthorRepository authorRepository;

  @Autowired
  private AuthorServiceImpl authorService;

  private Author newAuthor;
  private Author oldAuthor;

  @BeforeEach
  void beforeEach() {
    newAuthor = new Author("0", "new FirstName", "new LastName", now());
    oldAuthor = new Author("1", "old FirstName", "old LastName", now());
  }

  @Test
  void create() {
    when(authorRepository.save(newAuthor)).thenReturn(newAuthor);

    authorService.save(newAuthor);
    verify(authorRepository, times(1)).save(newAuthor);
  }

  @Test
  void update() {
    when(authorRepository.save(newAuthor)).thenReturn(newAuthor);

    authorService.save(newAuthor);
    verify(authorRepository, times(1)).save(newAuthor);
  }

  @Test
  void findAll() {
    List<Author> authors = new ArrayList<>();
    authors.add(new Author());
    authors.add(new Author());
    when(authorRepository.findAll()).thenReturn(authors);

    List<Author> actual = authorService.findAll();
    assertEquals(authors, actual);

    verify(authorRepository, times(1)).findAll();
  }
}
