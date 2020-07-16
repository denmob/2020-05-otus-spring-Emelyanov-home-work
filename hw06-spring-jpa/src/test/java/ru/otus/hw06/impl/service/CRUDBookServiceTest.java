package ru.otus.hw06.impl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Comment;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.repositories.BookRepositoryJpa;
import ru.otus.hw06.core.repositories.CommentRepositoryJpa;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.util.DateUtil.now;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CRUDBookService.class)
class CRUDBookServiceTest {

  @MockBean
  private BookRepositoryJpa bookRepositoryJpa;

  @MockBean
  private CommentRepositoryJpa commentRepositoryJpa;

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
    Author author = new Author(0L,"FirstName","LastName",now());
    Genre genre = new Genre(0L,"newGenre");
    Book book = new Book(4L,"Title",now(),author,genre);
    List<Comment> comments = new ArrayList<>();
    comments.add(new Comment(0L,"test",book));

    when(bookRepositoryJpa.getById(book.getId())).thenReturn(java.util.Optional.of(book));
    when(commentRepositoryJpa.getAllByBookId(book.getId())).thenReturn(comments);

    crudBookService.readWithComments(book.getId());
    verify(bookRepositoryJpa,times(1)).getById(book.getId());
    verify(commentRepositoryJpa,times(1)).getAllByBookId(book.getId());

  }

}
