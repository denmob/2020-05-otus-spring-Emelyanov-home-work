package ru.otus.hw06.core.service;

import ru.otus.hw06.core.models.Genre;

import java.util.Optional;

public interface CRUDServiceGenre {
  Genre create(Genre entity);

  Optional<Genre> read(long id);

  boolean delete(long id);

  Genre update(Genre entity);
}
