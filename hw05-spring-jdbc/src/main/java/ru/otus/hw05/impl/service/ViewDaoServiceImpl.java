package ru.otus.hw05.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw05.core.domain.Author;
import ru.otus.hw05.core.domain.Book;
import ru.otus.hw05.core.domain.Genre;
import ru.otus.hw05.core.service.OutputPrintService;
import ru.otus.hw05.core.service.ViewDaoService;
import ru.otus.hw05.impl.dao.AuthorDaoJdbc;
import ru.otus.hw05.impl.dao.BookDaoJdbc;
import ru.otus.hw05.impl.dao.GenreDaoJdbc;

@Service
@RequiredArgsConstructor
public class ViewDaoServiceImpl implements ViewDaoService {

  private final AuthorDaoJdbc authorDaoJdbc;
  private final BookDaoJdbc bookDaoJdbc;
  private final GenreDaoJdbc genreDaoJdbc;
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
