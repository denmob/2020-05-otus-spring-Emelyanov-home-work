package ru.otus.hw08.core.service;

import java.util.Optional;

public interface CRUDServiceGeneric<T> {
  T create(T entity);

  Optional<T> read(String id);

  T update(T entity);

  void delete(String id);
}
