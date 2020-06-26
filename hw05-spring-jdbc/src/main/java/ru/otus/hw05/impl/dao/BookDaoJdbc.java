package ru.otus.hw05.impl.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.core.dao.BookDao;
import ru.otus.hw05.core.domain.Book;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

  private final JdbcOperations jdbc;

  @Override
  public int count() {
    return 0;
  }

  @Override
  public void insert(Book book) {

  }

  @Override
  public Book getById(long id) {
    return null;
  }

  @Override
  public List<Book> getAll() {
    return null;
  }

  @Override
  public void deleteById(long id) {

  }
}
