package ru.otus.hw14.service;

import ru.otus.hw14.model.table.Author;

import java.util.Optional;

public interface AuthorCrudService {

  Optional<Author> findById(String id);

  Iterable<Author> findAll();

  Author save(Author entity);

  void delete(Author entity);

  void deleteAll();
}
