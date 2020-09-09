package ru.otus.hw14.service;

import ru.otus.hw14.model.document.Genre;

import java.util.Optional;

public interface GenreMongoService {

  Optional<Genre> findById(String id);

  Iterable<Genre> findAll();

  Genre save(Genre entity);

  void delete(Genre entity);

  void deleteAll();
}
