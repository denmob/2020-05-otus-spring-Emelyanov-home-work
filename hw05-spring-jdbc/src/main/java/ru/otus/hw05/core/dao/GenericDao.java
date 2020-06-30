package ru.otus.hw05.core.dao;

import java.util.List;

public interface GenericDao<T> {

  int count();

  void insert(T entity);

  T getById(long id);

  List<T> getAll();

  void deleteById(long id);
}
