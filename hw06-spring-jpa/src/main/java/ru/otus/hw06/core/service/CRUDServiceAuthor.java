package ru.otus.hw06.core.service;

import ru.otus.hw06.core.models.Author;

import java.util.Optional;

public interface CRUDServiceAuthor {
  Author create(Author entity);

  Optional<Author> read(long id);

  boolean delete(long id);

  Author update(Author entity);
}
