package ru.otus.hw07.impl.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw07.core.models.Author;
import ru.otus.hw07.core.models.Book;
import ru.otus.hw07.core.models.Comment;
import ru.otus.hw07.core.models.Genre;
import ru.otus.hw07.core.repositories.AuthorRepository;
import ru.otus.hw07.core.repositories.BookRepository;
import ru.otus.hw07.core.repositories.CommentRepository;
import ru.otus.hw07.core.repositories.GenreRepository;
import ru.otus.hw07.core.service.OutputPrintService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootTest(classes = ViewRepositoryServiceImpl.class)
class ViewRepositoryServiceImplTest {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @MockBean
  private AuthorRepository authorRepositoryJpa;

  @MockBean
  private BookRepository bookRepositoryJpa;

  @MockBean
  private GenreRepository genreRepositoryJpa;

  @MockBean
  private CommentRepository commentRepository;

  @MockBean
  private OutputPrintService consolePrintService;

  @Autowired
  private ViewRepositoryServiceImpl viewRepositoryService;

  @SneakyThrows
  @Test
  void printTableBooks() {
    Book book = Book.builder()
        .title("title")
        .date(convertStringToDate("2020-01-01"))
        .build();
    List<Book> books = new ArrayList<>();
    books.add(book);

    Mockito.when(bookRepositoryJpa.findAll()).thenReturn(books);
    Mockito.doNothing().when(consolePrintService).printlnMessage(book.toString());

    viewRepositoryService.printTableBooks();

    Mockito.verify(consolePrintService, Mockito.times(1)).printlnMessage(book.toString());
  }

  @SneakyThrows
  @Test
  void printTableAuthors() {
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    Author author = new Author(4L, "Evgeny", "Borisov", sf.parse("1978-10-03"));
    List<Author> authors = new ArrayList<>();
    authors.add(author);
    Mockito.when(authorRepositoryJpa.findAll()).thenReturn(authors);
    Mockito.doNothing().when(consolePrintService).printlnMessage(author.toString());

    viewRepositoryService.printTableAuthors();

    Mockito.verify(consolePrintService, Mockito.times(1)).printlnMessage(author.toString());
  }

  @Test
  void printTableGenres() {
    Genre genre = new Genre();
    genre.setName("testGenre");

    List<Genre> genres = new ArrayList<>();
    genres.add(genre);
    Mockito.when(genreRepositoryJpa.findAll()).thenReturn(genres);
    Mockito.doNothing().when(consolePrintService).printlnMessage(genre.toString());

    viewRepositoryService.printTableGenres();

    Mockito.verify(consolePrintService, Mockito.times(1)).printlnMessage(genre.toString());
  }

  @Test
  void printTableComments() {
    List<Comment> comments = new ArrayList<>();
    Comment comment = new Comment(0L, "test",null);
    comments.add(comment);

    Mockito.when(commentRepository.findAll()).thenReturn(comments);
    Mockito.doNothing().when(consolePrintService).printlnMessage(comment.toString());

    viewRepositoryService.printTableComments();

    Mockito.verify(consolePrintService, Mockito.times(1)).printlnMessage(comment.toString());
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return DATE_FORMAT.parse(date);
  }
}
