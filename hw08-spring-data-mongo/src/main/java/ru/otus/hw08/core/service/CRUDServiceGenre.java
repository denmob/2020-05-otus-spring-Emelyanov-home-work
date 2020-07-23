package ru.otus.hw08.core.service;

import ru.otus.hw08.core.models.Genre;

import java.util.Optional;

public interface CRUDServiceGenre {
  Genre create(Genre entity);

  Optional<Genre> read(String id);

  Genre update(Genre entity);

  boolean delete(String id);
}
