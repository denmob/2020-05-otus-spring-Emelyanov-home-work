package ru.otus.hw09.service;

import ru.otus.hw09.model.Genre;

import java.util.Optional;

public interface GenreService {
  Genre create(Genre entity);

  Optional<Genre> readNameEquals(String id);

  Genre update(Genre entity);

  boolean deleteNameEquals(String name);
}
