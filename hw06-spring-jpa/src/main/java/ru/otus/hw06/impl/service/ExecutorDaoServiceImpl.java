package ru.otus.hw06.impl.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.service.ExecutorDaoService;
import ru.otus.hw06.impl.repositories.AuthorRepositoryJpaImpl;
import ru.otus.hw06.impl.repositories.BookRepositoryJpaImpl;
import ru.otus.hw06.impl.repositories.GenreRepositoryJpaImpl;

@Service
@RequiredArgsConstructor
public class ExecutorDaoServiceImpl implements ExecutorDaoService {

  private final AuthorRepositoryJpaImpl authorDaoJdbc;
  private final BookRepositoryJpaImpl bookDaoJdbc;
  private final GenreRepositoryJpaImpl genreDaoJdbc;

  @Override
  public boolean insertBook(@NonNull Book book) {
    bookDaoJdbc.insert(book);
    return true;
  }

  @Override
  public boolean deleteBook(long id) {
    bookDaoJdbc.deleteById(id);
    return true;
  }

  @Override
  public boolean insertAuthor(@NonNull Author author) {
    authorDaoJdbc.insert(author);
    return true;
  }

  @Override
  public boolean deleteAuthor(long id) {
    authorDaoJdbc.deleteById(id);
    return true;
  }

  @Override
  public boolean insertGenre(@NonNull Genre genre) {
    genreDaoJdbc.insert(genre);
    return true;
  }

  @Override
  public boolean deleteGenre(long id) {
    genreDaoJdbc.deleteById(id);
    return true;
  }

}
