package ru.otus.hw05.impl.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.core.dao.AuthorDao;
import ru.otus.hw05.core.domain.Author;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

  private final JdbcOperations jdbc;

  @Override
  public int count() {
    return 0;
  }

  @Override
  public void insert(Author author) {

  }

  @Override
  public Author getById(long id) {
    return null;
  }

  @Override
  public List<Author> getAll() {
    return null;
  }

  @Override
  public void deleteById(long id) {

  }
}
