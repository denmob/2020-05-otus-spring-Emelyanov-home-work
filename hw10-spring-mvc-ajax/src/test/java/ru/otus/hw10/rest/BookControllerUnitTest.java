package ru.otus.hw10.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.otus.hw10.model.Author;
import ru.otus.hw10.model.Book;
import ru.otus.hw10.model.Genre;
import ru.otus.hw10.rest.dto.BookDto;
import ru.otus.hw10.service.AuthorServiceImpl;
import ru.otus.hw10.service.BookServiceImpl;
import ru.otus.hw10.service.GenreServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class, BookController.class})
class BookControllerUnitTest {

  @MockBean
  private BookServiceImpl bookService;

  @MockBean
  private AuthorServiceImpl authorService;

  @MockBean
  private GenreServiceImpl genreService;

  @Autowired
  private BookController bookController;

  private Book book;
  private final Author author = new Author("0", "FirstName", "LastName", now());
  private final Genre genre = new Genre("0", "newGenre");

  @BeforeEach
  void beforeEach() {
    book = Book.builder().id("123").title("title").author(author).genre(genre).build();
  }


  @Test
  void getBooks() {
    int countBook = 1;

    List<Book> books = new ArrayList<>();
    books.add(book);
    Page<Book> bookPage = new PageImpl<>(books);
    when(bookService.getLastAddedBooks(countBook)).thenReturn(bookPage);

    List<BookDto> bookDto = bookController.getBooks(countBook);

    assertEquals(1, bookDto.size());
    assertEquals(book.getId(), bookDto.get(0).getId());

    verify(bookService, times(1)).getLastAddedBooks(countBook);
  }

  @Test
  void edit() {
    when(bookService.readBookById(book.getId())).thenReturn(Optional.ofNullable(book));

    BookDto bookDto = bookController.edit(book.getId());

    assertNotNull(bookDto);
    assertEquals(book.getId(), bookDto.getId());

    verify(bookService, times(1)).readBookById(book.getId());
  }

  @Test
  void save() {
    BookDto bookDto = BookDto.toDto(book);

    when(authorService.findById(bookDto.getAuthor().getId())).thenReturn(Optional.ofNullable(bookDto.getAuthor()));
    when(genreService.findById(bookDto.getGenre().getId())).thenReturn(Optional.ofNullable(bookDto.getGenre()));
    when(bookService.save(book)).thenReturn(book);

    BookDto bookDtoActual =  bookController.save(bookDto);

    assertEquals(bookDto,bookDtoActual);

    verify(authorService, times(1)).findById(bookDto.getAuthor().getId());
    verify(genreService, times(1)).findById(bookDto.getGenre().getId());
    verify(bookService,times(1)).save(book);
  }

  @Test
  void delete() {
    when(bookService.deleteBookById(book.getId())).thenReturn(true);

    bookController.delete(book.getId());

    verify(bookService, times(1)).deleteBookById(book.getId());
  }
}
