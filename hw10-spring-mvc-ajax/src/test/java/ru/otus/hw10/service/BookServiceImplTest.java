package ru.otus.hw10.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.otus.hw10.model.Author;
import ru.otus.hw10.model.Book;
import ru.otus.hw10.model.Genre;
import ru.otus.hw10.repository.BookRepository;
import ru.otus.hw10.service.BookServiceImpl;


import java.util.Optional;

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
  void beforeEach() {
    Author newAuthor = new Author("0", "FirstName", "LastName", now());
    Genre newGenre = new Genre("0", "newGenre");
    newBook = new Book("0", "Title new", now(), newAuthor, newGenre);
    oldBook = new Book("1", "Title old", now(), newAuthor, newGenre);
  }

  @Test
  void create() {
    when(bookRepository.save(newBook)).thenReturn(newBook);

    bookService.save(newBook);
    verify(bookRepository, times(1)).save(newBook);
  }


  @Test
  void update() {
    when(bookRepository.save(newBook)).thenReturn(newBook);

    bookService.save(newBook);
    verify(bookRepository, times(1)).save(newBook);
  }

  @Test
  void getLastAddedBooks() {
    PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
    when(bookRepository.findAll(pageRequest)).thenReturn(any());

    bookService.getLastAddedBooks(pageRequest.getPageSize());
    verify(bookRepository, times(1)).findAll(pageRequest);
  }

  @Test
  void deleteBookById() {
    when(bookRepository.deleteBookById(newBook.getId())).thenReturn(1L);

    bookService.deleteBookById(newBook.getId());
    verify(bookRepository, times(1)).deleteBookById(newBook.getId());
  }

  @Test
  void readBookById() {
    when(bookRepository.findById(oldBook.getId())).thenReturn(Optional.of(oldBook));

    bookService.readBookById(oldBook.getId());

    verify(bookRepository, times(1)).findById(oldBook.getId());
  }
}
