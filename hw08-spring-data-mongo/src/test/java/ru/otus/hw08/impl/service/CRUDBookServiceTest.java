package ru.otus.hw08.impl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw08.core.dto.BookWithComments;
import ru.otus.hw08.core.models.Author;
import ru.otus.hw08.core.models.Book;
import ru.otus.hw08.core.models.Comment;
import ru.otus.hw08.core.models.Genre;
import ru.otus.hw08.core.repositories.BookRepository;
import ru.otus.hw08.core.repositories.CommentRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.util.DateUtil.now;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CRUDBookService.class})
class CRUDBookServiceTest {

  @MockBean
  private BookRepository bookRepository;

  @MockBean
  private CommentRepository commentRepository;

  @Autowired
  private CRUDBookService crudBookService;

  private Book newBook;
  private Book oldBook;

  @BeforeEach
  void beforeEach(){
    Author newAuthor = new Author("0", "FirstName", "LastName", now());
    Genre newGenre = new Genre("0", "newGenre");
    newBook = new Book("0","Title new",now(), newAuthor, newGenre);
    oldBook = new Book("1","Title old",now(), newAuthor, newGenre);
  }

  @Test
  void create() {
    when(bookRepository.save(newBook)).thenReturn(newBook);

    crudBookService.create(newBook);
    verify(bookRepository,times(1)).save(newBook);
  }

  @Test
  void read() {
    when(bookRepository.findByTitleEquals(oldBook.getTitle())).thenReturn(any());

    crudBookService.readByTitleEquals(oldBook.getTitle());
    verify(bookRepository,times(1)).findByTitleEquals(oldBook.getTitle());
  }

  @Test
  void delete() {
    when(bookRepository.findByTitleEquals(oldBook.getTitle())).thenReturn(Optional.ofNullable(oldBook));
    when(bookRepository.deleteBookById(oldBook.getId())).thenReturn(1L);
    when(commentRepository.deleteCommentAllByBookId(oldBook.getId())).thenReturn(1L);

    crudBookService.deleteByTitleEquals(oldBook.getTitle());
    verify(bookRepository,times(1)).deleteBookById(oldBook.getId());
    verify(commentRepository,times(1)).deleteCommentAllByBookId(oldBook.getId());
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
    comments.add(new Comment());
    BookWithComments bookWithComments = new BookWithComments(oldBook,comments);
    when(bookRepository.findByTitleEquals(oldBook.getTitle())).thenReturn(Optional.ofNullable(oldBook));
    when(commentRepository.findAllByBookId(oldBook.getId())).thenReturn(comments);

    Assertions.assertEquals(bookWithComments,crudBookService.readWithComments(oldBook.getTitle()).get());
  }
}
