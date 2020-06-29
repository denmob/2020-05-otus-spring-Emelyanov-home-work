package ru.otus.hw05.impl.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

import java.text.SimpleDateFormat;

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

  @SneakyThrows
  @Test
  @DisplayName("found AuthorId and GenreId for insert book")
  void insertBook() {
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    Book book = new Book(4L, 1L, 1L, "TestBook", sf.parse("2020-06-29"));

    Mockito.when(authorDaoJdbc.getById(book.getAuthorId())).thenReturn(new Author());
    Mockito.when(genreDaoJdbc.getById(book.getGenreId())).thenReturn(new Genre());
    Mockito.doNothing().when(bookDaoJdbc).insert(book);

    Assertions.assertTrue(executorDaoService.insertBook(book));

    Mockito.verify(bookDaoJdbc, Mockito.times(1)).insert(book);
  }

  @SneakyThrows
  @Test
  @DisplayName("not found AuthorId or GenreId for insert book")
  void insertBookNotFound() {
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    Book book = new Book(4L, 1L, 1L, "TestBook", sf.parse("2020-06-29"));

    Mockito.doNothing().when(bookDaoJdbc).insert(book);

    Assertions.assertFalse(executorDaoService.insertBook(book));
    Mockito.verify(bookDaoJdbc, Mockito.times(0)).insert(book);
  }

  @Test
  @DisplayName("found id for delete")
  void deleteBook() {
    long id = 1;
    Mockito.when(bookDaoJdbc.getById(id)).thenReturn(new Book());
    Mockito.doNothing().when(bookDaoJdbc).deleteById(id);

    Assertions.assertTrue(executorDaoService.deleteBook(id));

    Mockito.verify(bookDaoJdbc, Mockito.times(1)).deleteById(id);
  }

  @Test
  @DisplayName("not found id for delete")
  void deleteBookNotFound() {
    long id = 1;
    Mockito.doNothing().when(bookDaoJdbc).deleteById(id);

    Assertions.assertFalse(executorDaoService.deleteBook(id));

    Mockito.verify(bookDaoJdbc, Mockito.times(0)).deleteById(id);
  }

  @Test
  void insertAuthor() {
    Author author = new Author();
    Mockito.doNothing().when(authorDaoJdbc).insert(author);

    Assertions.assertTrue(executorDaoService.insertAuthor(author));

    Mockito.verify(authorDaoJdbc, Mockito.times(1)).insert(author);
  }

  @Test
  @DisplayName("not found id for delete")
  void deleteAuthorNotFound() {
    long id = 1;
    Mockito.doNothing().when(authorDaoJdbc).deleteById(id);

    Assertions.assertFalse(executorDaoService.deleteAuthor(id));

    Mockito.verify(authorDaoJdbc, Mockito.times(0)).deleteById(id);
  }

  @Test
  @DisplayName("found id for delete")
  void deleteAuthor() {
    long id = 1;
    Mockito.when(authorDaoJdbc.getById(id)).thenReturn(new Author());
    Mockito.doNothing().when(authorDaoJdbc).deleteById(id);

    Assertions.assertTrue(executorDaoService.deleteAuthor(id));

    Mockito.verify(authorDaoJdbc, Mockito.times(1)).deleteById(id);
  }

  @Test
  void insertGenre() {
    Genre genre = new Genre();
    Mockito.doNothing().when(genreDaoJdbc).insert(genre);

    Assertions.assertTrue( executorDaoService.insertGenre(genre));

    Mockito.verify(genreDaoJdbc, Mockito.times(1)).insert(genre);
  }

  @Test
  @DisplayName("not found id for delete")
  void deleteGenreNotFound() {
    long id = 1;
    Mockito.doNothing().when(genreDaoJdbc).deleteById(id);

    Assertions.assertFalse(executorDaoService.deleteGenre(id));

    Mockito.verify(genreDaoJdbc, Mockito.times(0)).deleteById(id);
  }

  @Test
  @DisplayName("found id for delete")
  void deleteGenre() {
    long id = 1;
    Mockito.when(genreDaoJdbc.getById(id)).thenReturn(new Genre());
    Mockito.doNothing().when(genreDaoJdbc).deleteById(id);

    Assertions.assertTrue(executorDaoService.deleteGenre(id));

    Mockito.verify(genreDaoJdbc, Mockito.times(1)).deleteById(id);
  }
}
