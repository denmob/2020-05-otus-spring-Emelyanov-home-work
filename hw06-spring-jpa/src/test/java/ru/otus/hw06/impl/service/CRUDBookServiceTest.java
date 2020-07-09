package ru.otus.hw06.impl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.repositories.BookRepositoryJpa;
import static org.assertj.core.util.DateUtil.now;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CRUDBookService.class)
class CRUDBookServiceTest {

  @MockBean
  private BookRepositoryJpa bookRepositoryJpa;

  @Autowired
  private CRUDBookService crudBookService;

  @Test
  void create() {
    Author author = new Author(0L,"FirstName","LastName",now());
    Genre genre = new Genre(0L,"newGenre");
    Book book = new Book(0L,"Title",now(),author,genre);
    when(bookRepositoryJpa.insert(book)).thenReturn(book);

    crudBookService.create(book);
    verify(bookRepositoryJpa,times(1)).insert(book);
  }

  @Test
  void read() {
    long id = 1L;
    when(bookRepositoryJpa.getById(id)).thenReturn(any());

    crudBookService.read(id);
    verify(bookRepositoryJpa,times(1)).getById(id);
  }

  @Test
  void delete() {
    long id = 1L;
    when(bookRepositoryJpa.deleteById(id)).thenReturn(true);

    crudBookService.delete(id);
    verify(bookRepositoryJpa,times(1)).deleteById(id);
  }

  @Test
  void update() {
    Author author = new Author(0L,"FirstName","LastName",now());
    Genre genre = new Genre(0L,"newGenre");
    Book book = new Book(3L,"Title",now(),author,genre);
    when(bookRepositoryJpa.insert(book)).thenReturn(book);

    crudBookService.update(book);
    verify(bookRepositoryJpa,times(1)).insert(book);
  }

  @Test
  void readWithComments() {
    long id = 1L;
    when(bookRepositoryJpa.getByIdWithComments(id)).thenReturn(any());

    crudBookService.readWithComments(id);
    verify(bookRepositoryJpa,times(1)).getByIdWithComments(id);

  }
}
