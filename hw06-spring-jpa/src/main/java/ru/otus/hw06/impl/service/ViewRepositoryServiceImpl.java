package ru.otus.hw06.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.service.OutputPrintService;
import ru.otus.hw06.core.service.ViewRepositoryService;
import ru.otus.hw06.impl.repositories.AuthorRepositoryJpaImpl;
import ru.otus.hw06.impl.repositories.BookRepositoryJpaImpl;
import ru.otus.hw06.impl.repositories.GenreRepositoryJpaImpl;

@Service
@RequiredArgsConstructor
public class ViewRepositoryServiceImpl implements ViewRepositoryService {

  private final AuthorRepositoryJpaImpl authorDaoJdbc;
  private final BookRepositoryJpaImpl bookDaoJdbc;
  private final GenreRepositoryJpaImpl genreDaoJdbc;
  private final OutputPrintService outputPrintService;

  @Override
  public void printTableBooks() {
    for (Book book:bookDaoJdbc.getAll()) {
      outputPrintService.printlnMessage(book.toString());
    }
  }

  @Override
  public void printTableAuthors() {
    for (Author author:authorDaoJdbc.getAll()) {
      outputPrintService.printlnMessage(author.toString());
    }
  }

  @Override
  public void printTableGenres() {
    for (Genre genre:genreDaoJdbc.getAll()) {
      outputPrintService.printlnMessage(genre.toString());
    }
  }
}
