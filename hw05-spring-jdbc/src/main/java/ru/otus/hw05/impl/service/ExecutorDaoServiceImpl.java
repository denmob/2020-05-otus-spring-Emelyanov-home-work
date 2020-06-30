package ru.otus.hw05.impl.service;

import lombok.NonNull;
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
  public boolean insertBook(@NonNull Book book) {
    if (authorDaoJdbc.getById(book.getAuthorId()) != null && genreDaoJdbc.getById(book.getGenreId()) != null) {
      if (book.getId() == 0L) {
        book.setId(bookDaoJdbc.count() + 1L);
      }
      bookDaoJdbc.insert(book);
      return true;
    }
    return false;
  }

  @Override
  public boolean deleteBook(long id) {
    if (bookDaoJdbc.getById(id) != null) {
      bookDaoJdbc.deleteById(id);
      return true;
    }
    return false;
  }

  @Override
  public boolean insertAuthor(@NonNull Author author) {
    if (author.getId() == 0L) {
      author.setId(authorDaoJdbc.count() + 1L);
    }
    authorDaoJdbc.insert(author);
    return true;
  }

  @Override
  public boolean deleteAuthor(long id) {
    if (authorDaoJdbc.getById(id) != null) {
      authorDaoJdbc.deleteById(id);
      return true;
    }
    return false;
  }

  @Override
  public boolean insertGenre(@NonNull Genre genre) {
    if (genre.getId() == 0L) {
      genre.setId(genreDaoJdbc.count() + 1L);
    }
    genreDaoJdbc.insert(genre);
    return true;
  }

  @Override
  public boolean deleteGenre(long id) {
    if (genreDaoJdbc.getById(id) != null) {
      genreDaoJdbc.deleteById(id);
      return true;
    }
    return false;
  }
}
