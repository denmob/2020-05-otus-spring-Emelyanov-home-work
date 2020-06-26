package ru.otus.hw05.core.dao;

import ru.otus.hw05.core.domain.Author;

import java.util.List;

public interface AuthorDao {

  int count();

  void insert(Author author);

  Author getById(long id);

  List<Author> getAll();

  void deleteById(long id);

}
