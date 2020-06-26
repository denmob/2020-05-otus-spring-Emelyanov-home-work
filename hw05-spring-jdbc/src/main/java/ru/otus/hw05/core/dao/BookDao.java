package ru.otus.hw05.core.dao;

import ru.otus.hw05.core.domain.Book;

import java.util.List;

public interface BookDao {

  int count();

  void insert(Book book);

  Book getById(long id);

  List<Book> getAll();

  void deleteById(long id);
}
