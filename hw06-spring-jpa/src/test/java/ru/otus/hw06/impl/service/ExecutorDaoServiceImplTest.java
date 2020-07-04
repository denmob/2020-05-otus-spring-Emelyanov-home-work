package ru.otus.hw06.impl.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.impl.repositories.AuthorRepositoryJpaImpl;
import ru.otus.hw06.impl.repositories.BookRepositoryJpaImpl;
import ru.otus.hw06.impl.repositories.GenreRepositoryJpaImpl;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Import({BookRepositoryJpaImpl.class, GenreRepositoryJpaImpl.class, AuthorRepositoryJpaImpl.class})
class ExecutorDaoServiceImplTest {

  @MockBean
  private GenreRepositoryJpaImpl genreDaoJdbc;

  @MockBean
  private BookRepositoryJpaImpl bookDaoJdbc;

  @MockBean
  private AuthorRepositoryJpaImpl authorDaoJdbc;

  @Autowired
  private ExecutorDaoServiceImpl executorDaoService;

  @SneakyThrows
  @Test
  @DisplayName("found AuthorId and GenreId for insert book")
  void insertBookWithId() {
//    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//    Book book = new Book(4L, 1L, 1L, "TestBook", sf.parse("2020-06-29"));
//
//    Mockito.when(authorDaoJdbc.getById(book.getAuthorId())).thenReturn(new Author());
//    Mockito.when(genreDaoJdbc.getById(book.getGenreId())).thenReturn(new Genre());
//    Mockito.doNothing().when(bookDaoJdbc).insert(book);
//
//    Assertions.assertTrue(executorDaoService.insertBook(book));
//    Assertions.assertEquals(4L, book.getId());
//    Mockito.verify(bookDaoJdbc, Mockito.times(1)).insert(book);
  }

  @SneakyThrows
  @Test
  @DisplayName("found AuthorId and GenreId for insert book")
  void insertBookWithoutId() {
//    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//    Book book = new Book(0L, 1L, 1L, "TestBook", sf.parse("2020-06-29"));
//
//    Mockito.when(authorDaoJdbc.getById(book.getAuthorId())).thenReturn(new Author());
//    Mockito.when(genreDaoJdbc.getById(book.getGenreId())).thenReturn(new Genre());
//    Mockito.doNothing().when(bookDaoJdbc).insert(book);
//
//    Assertions.assertTrue(executorDaoService.insertBook(book));
//    Assertions.assertEquals(1L, book.getId());
//    Mockito.verify(bookDaoJdbc, Mockito.times(1)).insert(book);
  }

  @SneakyThrows
  @Test
  @DisplayName("not found AuthorId or GenreId for insert book")
  void insertBookNotFound() {
//    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//    Book book = new Book(4L, 1L, 1L, "TestBook", sf.parse("2020-06-29"));
//
//    Mockito.doNothing().when(bookDaoJdbc).insert(book);
//
//    Assertions.assertFalse(executorDaoService.insertBook(book));
//    Mockito.verify(bookDaoJdbc, Mockito.times(0)).insert(book);
  }

  @Test
  @DisplayName("found id for delete")
  void deleteBook() {
//    long id = 1;
//    Mockito.when(bookDaoJdbc.getById(id)).thenReturn(new Book());
//    Mockito.doNothing().when(bookDaoJdbc).deleteById(id);
//
//    Assertions.assertTrue(executorDaoService.deleteBook(id));
//
//    Mockito.verify(bookDaoJdbc, Mockito.times(1)).deleteById(id);
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
//    long id = 1;
//    Mockito.when(authorDaoJdbc.getById(id)).thenReturn(new Author());
//    Mockito.doNothing().when(authorDaoJdbc).deleteById(id);
//
//    Assertions.assertTrue(executorDaoService.deleteAuthor(id));
//
//    Mockito.verify(authorDaoJdbc, Mockito.times(1)).deleteById(id);
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
//    long id = 1;
//    Mockito.when(genreDaoJdbc.getById(id)).thenReturn(new Genre());
//    Mockito.doNothing().when(genreDaoJdbc).deleteById(id);
//
//    Assertions.assertTrue(executorDaoService.deleteGenre(id));
//
//    Mockito.verify(genreDaoJdbc, Mockito.times(1)).deleteById(id);
  }
}
