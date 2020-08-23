package ru.otus.hw11.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.dto.BookDto;
import ru.otus.hw11.model.Author;
import ru.otus.hw11.model.Book;
import ru.otus.hw11.model.Genre;
import ru.otus.hw11.repository.AuthorRepository;
import ru.otus.hw11.repository.BookRepository;
import ru.otus.hw11.repository.GenreRepository;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookController.class, BookRepository.class, AuthorRepository.class, GenreRepository.class})
class BookControllerTest {

  @Autowired
  private BookController bookController;

  @MockBean
  private BookRepository bookRepository;

  @MockBean
  private AuthorRepository authorRepository;

  @MockBean
  private GenreRepository genreRepository;

  @Test
  void getBooks() {
    Book book1 = Book.builder().id("1").title("new book 1").build();
    Book book2 = Book.builder().id("2").title("new book 2").build();

    when(bookRepository.findAll()).thenReturn(Flux.just(book1,book2));

    Flux<BookDto>  actualBookDtoFlux = bookController.getBooks(0,5);

    assertEquals(BookDto.toDto(book2), actualBookDtoFlux.blockFirst());
    verify(bookRepository,times(1)).findAll();
  }

  @Test
  void post() {
    Author author = Author.builder().firstName("firstName1").lastName("").birthday(new Date()).build();
    Genre genre = Genre.builder().name("genre1").build();
    Book book = Book.builder().id("1").title("new book 1").author(author).genre(genre).build();

    when(authorRepository.findById(book.getAuthor().getId())).thenReturn(Mono.just(author));
    when(genreRepository.findById(book.getGenre().getId())).thenReturn(Mono.just(genre));
    when(bookRepository.save(book)).thenReturn(Mono.just(book));

    assertEquals(BookDto.toDto(book),  bookController.post(BookDto.toDto(book)).block());

    verify(authorRepository,times(1)).findById(book.getAuthor().getId());
    verify(genreRepository,times(1)).findById(book.getGenre().getId());
    verify(bookRepository,times(1)).save(book);
  }
}
