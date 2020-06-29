package ru.otus.hw05.impl.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.core.dao.BookDao;
import ru.otus.hw05.core.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
@SuppressWarnings({"ConstantConditions", "SqlDialectInspection"})
public class BookDaoJdbc implements BookDao {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public int count() {
    String sql = "select count(*) from books";
    SqlParameterSource namedParameters = new MapSqlParameterSource();
    return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
  }

  @Override
  public void insert(Book book) {
    String sql = "insert into books (id, author_id, genre_id, title, date) values (:id, :authorId, :genreId, :title, :date)";
    Map<String, Object> namedParameters = new HashMap<>();
    namedParameters.put("id", book.getId());
    namedParameters.put("authorId", book.getAuthorId());
    namedParameters.put("genreId", book.getGenreId());
    namedParameters.put("title", book.getTitle());
    namedParameters.put("date", book.getDate());
    namedParameterJdbcTemplate.update(sql, namedParameters);
  }

  @Override
  public Book getById(long id) {
    String sql = "select * from books where id = :id";
    Map<String, Object> namedParameters = Collections.singletonMap("id", id);
    return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new BookMapper());
  }

  @Override
  public List<Book> getAll() {
    String sql = "select * from books";
    return namedParameterJdbcTemplate.query(sql, new BookMapper());
  }

  @Override
  public void deleteById(long id) {
    String sql = "delete from books where id = :id";
    Map<String, Object> namedParameters = Collections.singletonMap("id", id);
    namedParameterJdbcTemplate.update(sql, namedParameters);
  }

  private static class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
      long id = resultSet.getLong("id");
      Long authorId = resultSet.getLong("author_id");
      Long genreId = resultSet.getLong("genre_id");
      String title = resultSet.getString("title");
      Date date = resultSet.getDate("date");
      return new Book(id, authorId, genreId, title, date);
    }
  }
}
