package ru.otus.hw05.impl.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.core.dao.AuthorDao;
import ru.otus.hw05.core.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
@SuppressWarnings({"ConstantConditions", "SqlDialectInspection"})
public class AuthorDaoJdbc implements AuthorDao {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public int count() {
    String sql = "select count(*) from authors";
    SqlParameterSource namedParameters = new MapSqlParameterSource();
    return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
  }

  @Override
  public void insert(Author author) {
    String sql = "insert into authors (id, firstName, lastName, birthday) values (:id, :firstName, :lastName, :birthday)";
    Map<String, Object> namedParameters = new HashMap<>();
    namedParameters.put("id", author.getId());
    namedParameters.put("firstName", author.getFirstName());
    namedParameters.put("lastName", author.getLastName());
    namedParameters.put("birthday", author.getBirthday());
    namedParameterJdbcTemplate.update(sql, namedParameters);
  }

  @Override
  public Author getById(long id) {
    String sql = "select * from authors where id = :id";
    Map<String, Object> namedParameters = Collections.singletonMap("id", id);
    return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new AuthorMapper());
  }

  @Override
  public List<Author> getAll() {
    String sql = "select * from authors";
    return namedParameterJdbcTemplate.query(sql, new AuthorMapper());
  }

  @Override
  public void deleteById(long id) {
    String sql = "delete from authors where id = :id";
    Map<String, Object> namedParameters = Collections.singletonMap("id", id);
    namedParameterJdbcTemplate.update(sql, namedParameters);
  }

  private static class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
      long id = resultSet.getLong("id");
      String firstName = resultSet.getString("firstName");
      String lastName = resultSet.getString("lastName");
      Date birthday = resultSet.getDate("birthday");
      return new Author(id, firstName, lastName, birthday);
    }
  }
}
