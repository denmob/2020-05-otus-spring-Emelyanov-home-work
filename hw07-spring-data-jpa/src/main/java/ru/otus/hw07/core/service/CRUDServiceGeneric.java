package ru.otus.hw07.core.service;

import java.util.Optional;

public interface CRUDServiceGeneric<T> {
  T create(T entity);

  Optional<T> read(long id);

  T update(T entity);

  void delete(long id);
}
