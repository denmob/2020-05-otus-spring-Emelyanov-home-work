package ru.otus.hw07.impl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw07.core.models.Author;
import ru.otus.hw07.core.models.Book;
import ru.otus.hw07.core.models.Comment;
import ru.otus.hw07.core.models.Genre;
import ru.otus.hw07.core.repositories.BookRepository;
import ru.otus.hw07.core.repositories.CommentRepository;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.util.DateUtil.now;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CRUDBookService.class)
class CRUDBookServiceTest {

  @MockBean
  private BookRepository bookRepository;

  @MockBean
  private CommentRepository commentRepository;

  @Autowired
  private CRUDBookService crudBookService;

  @Test
  void create() {
    Author author = new Author(0L,"FirstName","LastName",now());
    Genre genre = new Genre(0L,"newGenre");
    Book book = new Book(0L,"Title",now(),author,genre);
    when(bookRepository.save(book)).thenReturn(book);

    crudBookService.create(book);
    verify(bookRepository,times(1)).save(book);
  }

  @Test
  void read() {
    long id = 1L;
    when(bookRepository.findById(id)).thenReturn(any());

    crudBookService.read(id);
    verify(bookRepository,times(1)).findById(id);
  }

  @Test
  void delete() {
    long id = 1L;
    doNothing().when(bookRepository).deleteById(id);

    crudBookService.delete(id);
    verify(bookRepository,times(1)).deleteById(id);
  }

  @Test
  void update() {
    Author author = new Author(0L,"FirstName","LastName",now());
    Genre genre = new Genre(0L,"newGenre");
    Book book = new Book(3L,"Title",now(),author,genre);
    when(bookRepository.save(book)).thenReturn(book);

    crudBookService.update(book);
    verify(bookRepository,times(1)).save(book);
  }

  @Test
  void readWithComments() {
    Author author = new Author(0L,"FirstName","LastName",now());
    Genre genre = new Genre(0L,"newGenre");
    Book book = new Book(4L,"Title",now(),author,genre);
    List<Comment> comments = new ArrayList<>();
    comments.add(new Comment(0L,"test",book));

    when(bookRepository.findById(book.getId())).thenReturn(java.util.Optional.of(book));
    when(commentRepository.getAllByBookId(book.getId())).thenReturn(comments);

    crudBookService.readWithComments(book.getId());
    verify(bookRepository,times(1)).findById(book.getId());
    verify(commentRepository,times(1)).getAllByBookId(book.getId());

  }

}
