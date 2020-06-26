package ru.otus.hw05.impl.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.core.dao.GenreDao;
import ru.otus.hw05.core.domain.Genre;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

  private final JdbcOperations jdbc;

  @Override
  public int count() {
    return 0;
  }

  @Override
  public void insert(Genre genre) {

  }

  @Override
  public Genre getById(long id) {
    return null;
  }

  @Override
  public List<Genre> getAll() {
    return null;
  }

  @Override
  public void deleteById(long id) {

  }
}
