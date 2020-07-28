package ru.otus.hw09.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw09.dto.BookWithComments;
import ru.otus.hw09.model.Author;
import ru.otus.hw09.model.Book;
import ru.otus.hw09.model.Comment;
import ru.otus.hw09.model.Genre;
import ru.otus.hw09.repository.BookRepository;
import ru.otus.hw09.repository.CommentRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.util.DateUtil.now;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookServiceImpl.class})
class BookServiceImplTest {

  @MockBean
  private BookRepository bookRepository;

  @MockBean
  private CommentRepository commentRepository;

  @Autowired
  private BookServiceImpl bookService;

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

    bookService.create(newBook);
    verify(bookRepository,times(1)).save(newBook);
  }

  @Test
  void read() {
    when(bookRepository.findByTitleContains(oldBook.getTitle())).thenReturn(any());

    bookService.readByTitleContains(oldBook.getTitle());
    verify(bookRepository,times(1)).findByTitleContains(oldBook.getTitle());
  }

  @Test
  void delete() {
    when(bookRepository.findByTitleContains(oldBook.getTitle())).thenReturn(Optional.ofNullable(oldBook));
    when(bookRepository.deleteBookById(oldBook.getId())).thenReturn(1L);
    when(commentRepository.deleteCommentAllByBookId(oldBook.getId())).thenReturn(1L);

    bookService.deleteByTitleEquals(oldBook.getTitle());
    verify(bookRepository,times(1)).deleteBookById(oldBook.getId());
    verify(commentRepository,times(1)).deleteCommentAllByBookId(oldBook.getId());
  }

  @Test
  void update() {
    when(bookRepository.save(newBook)).thenReturn(newBook);

    bookService.update(newBook);
    verify(bookRepository,times(1)).save(newBook);
  }

  @Test
  void readWithComments() {
    List<Comment> comments = new ArrayList<>();
    comments.add(new Comment());
    BookWithComments bookWithComments = new BookWithComments(oldBook,comments);
    when(bookRepository.findByTitleContains(oldBook.getTitle())).thenReturn(Optional.ofNullable(oldBook));
    when(commentRepository.findAllByBookId(oldBook.getId())).thenReturn(comments);

    Assertions.assertEquals(bookWithComments, bookService.readWithComments(oldBook.getTitle()).get());
  }
}
