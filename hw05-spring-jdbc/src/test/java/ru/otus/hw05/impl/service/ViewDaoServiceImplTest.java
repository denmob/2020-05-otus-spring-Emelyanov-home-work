package ru.otus.hw05.impl.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw05.core.domain.Author;
import ru.otus.hw05.core.domain.Book;
import ru.otus.hw05.core.domain.Genre;
import ru.otus.hw05.core.service.OutputPrintService;
import ru.otus.hw05.impl.dao.AuthorDaoJdbc;
import ru.otus.hw05.impl.dao.BookDaoJdbc;
import ru.otus.hw05.impl.dao.GenreDaoJdbc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = ViewDaoServiceImpl.class)
class ViewDaoServiceImplTest {

  @MockBean
  private AuthorDaoJdbc authorDaoJdbc;

  @MockBean
  private BookDaoJdbc bookDaoJdbc;

  @MockBean
  private GenreDaoJdbc genreDaoJdbc;

  @MockBean
  private OutputPrintService outputPrintService;

  @Autowired
  private ViewDaoServiceImpl viewDaoService;

  @SneakyThrows
  @Test
  void printTableBooks() {
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    Book book = new Book(4L, 1L, 1L, "TestBook", sf.parse("2020-06-29"));
    List<Book> books = new ArrayList<>();
    books.add(book);
    Mockito.when(bookDaoJdbc.getAll()).thenReturn(books);
    Mockito.doNothing().when(outputPrintService).printlnMessage(book.toString());

    viewDaoService.printTableBooks();

    Mockito.verify(outputPrintService, Mockito.times(1)).printlnMessage(book.toString());
  }

  @SneakyThrows
  @Test
  void printTableAuthors() {
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    Author author = new Author(4L, "Evgeny", "Borisov", sf.parse("1978-10-03"));
    List<Author> authors = new ArrayList<>();
    authors.add(author);
    Mockito.when(authorDaoJdbc.getAll()).thenReturn(authors);
    Mockito.doNothing().when(outputPrintService).printlnMessage(author.toString());

    viewDaoService.printTableAuthors();

    Mockito.verify(outputPrintService, Mockito.times(1)).printlnMessage(author.toString());
  }

  @Test
  void printTableGenres() {
    Genre genre = new Genre();
    genre.setName("testGenre");

    List<Genre> genres = new ArrayList<>();
    genres.add(genre);
    Mockito.when(genreDaoJdbc.getAll()).thenReturn(genres);
    Mockito.doNothing().when(outputPrintService).printlnMessage(genre.toString());

    viewDaoService.printTableGenres();

    Mockito.verify(outputPrintService, Mockito.times(1)).printlnMessage(genre.toString());
  }
}
