package ru.otus.hw05.impl.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.core.dao.GenreDao;
import ru.otus.hw05.core.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@SuppressWarnings({"ConstantConditions", "SqlDialectInspection"})
public class GenreDaoJdbc implements GenreDao {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public int count() {
    String sql = "select count(*) from genres";
    SqlParameterSource namedParameters = new MapSqlParameterSource();
    return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
  }

  @Override
  public void insert(Genre genre) {
    String sql = "insert into genres (id, name) values (:id, :name)";
    Map<String, Object> namedParameters = new HashMap<>();
    namedParameters.put("id", genre.getId());
    namedParameters.put("name", genre.getName());
    namedParameterJdbcTemplate.update(sql, namedParameters);
  }

  @Override
  public Genre getById(long id) {
    String sql = "select * from genres where id = :id";
    Map<String, Object> namedParameters = Collections.singletonMap("id", id);
    return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new GenreMapper());
  }

  @Override
  public List<Genre> getAll() {
    String sql = "select * from genres";
    return namedParameterJdbcTemplate.query(sql, new GenreMapper());
  }

  @Override
  public void deleteById(long id) {
    String sql = "delete from genres where id = :id";
    Map<String, Object> namedParameters = Collections.singletonMap("id", id);
    namedParameterJdbcTemplate.update(sql, namedParameters);
  }

  private static class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
      long id = resultSet.getLong("id");
      String name = resultSet.getString("name");
      return new Genre(id, name);
    }
  }
}
