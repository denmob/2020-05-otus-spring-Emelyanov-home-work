package ru.otus.hw06.core.repositories;

import java.util.List;
import java.util.Optional;

public interface GenericRepositoryJpa<T> {
  long count();

  T insert(T entity);

  Optional<T> getById(long id);

  List<T> getAll();

  boolean deleteById(long id);
}
