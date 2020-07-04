package ru.otus.hw06.impl.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.service.OutputPrintService;
import ru.otus.hw06.impl.repositories.AuthorRepositoryJpaImpl;
import ru.otus.hw06.impl.repositories.BookRepositoryJpaImpl;
import ru.otus.hw06.impl.repositories.GenreRepositoryJpaImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = ViewRepositoryServiceImpl.class)
class ViewRepositoryServiceImplTest {

  @MockBean
  private AuthorRepositoryJpaImpl authorDaoJdbc;

  @MockBean
  private BookRepositoryJpaImpl bookDaoJdbc;

  @MockBean
  private GenreRepositoryJpaImpl genreDaoJdbc;

  @MockBean
  private OutputPrintService outputPrintService;

  @Autowired
  private ViewRepositoryServiceImpl viewDaoService;

  @SneakyThrows
  @Test
  void printTableBooks() {

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
