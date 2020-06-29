package ru.otus.hw05.impl.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.hw05.core.domain.Author;
import ru.otus.hw05.core.domain.Book;
import ru.otus.hw05.core.domain.Genre;
import ru.otus.hw05.impl.dao.AuthorDaoJdbc;
import ru.otus.hw05.impl.dao.BookDaoJdbc;
import ru.otus.hw05.impl.dao.GenreDaoJdbc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Import({BookDaoJdbc.class, GenreDaoJdbc.class, AuthorDaoJdbc.class})
class ExecutorDaoServiceImplTest {

  @MockBean
  private GenreDaoJdbc genreDaoJdbc;

  @MockBean
  private BookDaoJdbc bookDaoJdbc;

  @MockBean
  private AuthorDaoJdbc authorDaoJdbc;

  @Autowired
  private ExecutorDaoServiceImpl executorDaoService;

  @Test
  void insertBook() {
    Book book = new Book();
    Mockito.doNothing().when(bookDaoJdbc).insert(book);

    executorDaoService.insertBook(book);

    Mockito.verify(bookDaoJdbc, Mockito.times(1)).insert(book);
  }

  @Test
  void deleteBook() {
    long id = 1;
    Mockito.doNothing().when(bookDaoJdbc).deleteById(id);

    executorDaoService.deleteBook(id);

    Mockito.verify(bookDaoJdbc, Mockito.times(1)).deleteById(id);
  }

  @Test
  void insertAuthor() {
    Author author = new Author();
    Mockito.doNothing().when(authorDaoJdbc).insert(author);

    executorDaoService.insertAuthor(author);

    Mockito.verify(authorDaoJdbc, Mockito.times(1)).insert(author);
  }

  @Test
  void deleteAuthor() {
    long id = 1;
    Mockito.doNothing().when(authorDaoJdbc).deleteById(id);

    executorDaoService.deleteAuthor(id);

    Mockito.verify(authorDaoJdbc, Mockito.times(1)).deleteById(id);
  }

  @Test
  void insertGenre() {
    Genre genre = new Genre();
    Mockito.doNothing().when(genreDaoJdbc).insert(genre);

    executorDaoService.insertGenre(genre);

    Mockito.verify(genreDaoJdbc, Mockito.times(1)).insert(genre);
  }

  @Test
  void deleteGenre() {
    long id = 1;
    Mockito.doNothing().when(genreDaoJdbc).deleteById(id);

    executorDaoService.deleteGenre(id);

    Mockito.verify(genreDaoJdbc, Mockito.times(1)).deleteById(id);
  }
}
