package ru.otus.hw06.core.repositories;

import ru.otus.hw06.core.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepositoryJpa {
  long count();

  Genre insert(Genre entity);

  Optional<Genre> getById(long id);

  List<Genre> getAll();

  boolean deleteById(long id);
}
