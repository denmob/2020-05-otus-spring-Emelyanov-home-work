package ru.otus.hw09.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw09.model.Author;
import ru.otus.hw09.model.Book;
import ru.otus.hw09.model.Genre;
import ru.otus.hw09.repository.BookRepository;


import static org.assertj.core.util.DateUtil.now;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookServiceImpl.class})
class BookServiceImplTest {

  @MockBean
  private BookRepository bookRepository;

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

    bookService.save(newBook);
    verify(bookRepository,times(1)).save(newBook);
  }


  @Test
  void update() {
    when(bookRepository.save(newBook)).thenReturn(newBook);

    bookService.update(newBook);
    verify(bookRepository,times(1)).save(newBook);
  }

}
