package ru.otus.hw05.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw05.core.domain.Author;
import ru.otus.hw05.core.domain.Book;
import ru.otus.hw05.core.domain.Genre;
import ru.otus.hw05.core.service.ExecutorDaoService;
import ru.otus.hw05.impl.dao.AuthorDaoJdbc;
import ru.otus.hw05.impl.dao.BookDaoJdbc;
import ru.otus.hw05.impl.dao.GenreDaoJdbc;

@Service
@RequiredArgsConstructor
public class ExecutorDaoServiceImpl implements ExecutorDaoService {

  private final AuthorDaoJdbc authorDaoJdbc;
  private final BookDaoJdbc bookDaoJdbc;
  private final GenreDaoJdbc genreDaoJdbc;

  @Override
  public void insertBook(Book book) {
    bookDaoJdbc.insert(book);
  }

  @Override
  public void deleteBook(long id) {
    bookDaoJdbc.deleteById(id);
  }

  @Override
  public void insertAuthor(Author author) {
    authorDaoJdbc.insert(author);
  }

  @Override
  public void deleteAuthor(long id) {
    authorDaoJdbc.deleteById(id);
  }

  @Override
  public void insertGenre(Genre genre) {
    genreDaoJdbc.insert(genre);
  }

  @Override
  public void deleteGenre(long id) {
    genreDaoJdbc.deleteById(id);
  }
}
