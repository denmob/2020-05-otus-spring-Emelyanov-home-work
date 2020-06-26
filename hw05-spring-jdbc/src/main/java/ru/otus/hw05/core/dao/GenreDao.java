package ru.otus.hw05.core.dao;

import ru.otus.hw05.core.domain.Genre;

import java.util.List;

public interface GenreDao {

  int count();

  void insert(Genre genre);

  Genre getById(long id);

  List<Genre> getAll();

  void deleteById(long id);
}
