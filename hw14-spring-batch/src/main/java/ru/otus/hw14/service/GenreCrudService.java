package ru.otus.hw14.service;

import ru.otus.hw14.model.table.Genre;

import java.util.Optional;

public interface GenreCrudService {

  Optional<Genre> findById(String id);

  Optional<Genre> findByName(String name);

  Iterable<Genre> findAll();

  Genre save(Genre entity);

  void delete(Genre entity);

  void deleteAll();
}
