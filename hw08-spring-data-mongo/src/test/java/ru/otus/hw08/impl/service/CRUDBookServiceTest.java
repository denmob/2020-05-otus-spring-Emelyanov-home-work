package ru.otus.hw08.impl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw08.core.models.Author;
import ru.otus.hw08.core.models.Book;
import ru.otus.hw08.core.models.Comment;
import ru.otus.hw08.core.models.Genre;
import ru.otus.hw08.core.repositories.BookRepository;
import ru.otus.hw08.core.repositories.CommentRepository;


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

  private Book newBook;

  @BeforeEach
  void beforeEach(){
    Author newAuthor = new Author("0", "FirstName", "LastName", now());
    Genre newGenre = new Genre("0", "newGenre");
    newBook = new Book("0","Title",now(), newAuthor, newGenre,null);
  }

  @Test
  void create() {
    when(bookRepository.save(newBook)).thenReturn(newBook);

    crudBookService.create(newBook);
    verify(bookRepository,times(1)).save(newBook);
  }

  @Test
  void read() {
    String id = "1";
    when(bookRepository.findById(id)).thenReturn(any());

    crudBookService.read(id);
    verify(bookRepository,times(1)).findById(id);
  }

  @Test
  void delete() {
    String id = "1";
    doNothing().when(bookRepository).deleteById(id);

    crudBookService.delete(id);
    verify(bookRepository,times(1)).deleteById(id);
  }

  @Test
  void update() {
    when(bookRepository.save(newBook)).thenReturn(newBook);

    crudBookService.update(newBook);
    verify(bookRepository,times(1)).save(newBook);
  }

  @Test
  void readWithComments() {
    when(bookRepository.findById(newBook.getId())).thenReturn(java.util.Optional.of(newBook));

    crudBookService.readWithComments(newBook.getId());

    verify(bookRepository,times(1)).findById(newBook.getId());
  }
}
