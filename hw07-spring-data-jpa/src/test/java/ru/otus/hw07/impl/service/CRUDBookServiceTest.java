package ru.otus.hw07.impl.service;

import org.junit.jupiter.api.BeforeEach;
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

  private Book newBook;

  @BeforeEach
  void beforeEach(){
    Author newAuthor = new Author(0L, "FirstName", "LastName", now());
    Genre newGenre = new Genre(0L, "newGenre");
    newBook = new Book(0L,"Title",now(), newAuthor, newGenre);
  }

  @Test
  void create() {
    when(bookRepository.save(newBook)).thenReturn(newBook);

    crudBookService.create(newBook);
    verify(bookRepository,times(1)).save(newBook);
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
    when(bookRepository.save(newBook)).thenReturn(newBook);

    crudBookService.update(newBook);
    verify(bookRepository,times(1)).save(newBook);
  }

  @Test
  void readWithComments() {
    List<Comment> comments = new ArrayList<>();
    comments.add(new Comment(0L,"test",newBook));

    when(bookRepository.findById(newBook.getId())).thenReturn(java.util.Optional.of(newBook));
    when(commentRepository.getAllByBookId(newBook.getId())).thenReturn(comments);

    crudBookService.readWithComments(newBook.getId());
    verify(bookRepository,times(1)).findById(newBook.getId());
    verify(commentRepository,times(1)).getAllByBookId(newBook.getId());
  }

}
