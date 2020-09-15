package ru.otus.hw14.service;

import ru.otus.hw14.model.entity.GenreEntity;

import java.util.Optional;

public interface GenreCrudService {

  Optional<GenreEntity> findById(String id);

  Optional<GenreEntity> findByName(String name);

  Iterable<GenreEntity> findAll();

  GenreEntity save(GenreEntity entity);

  void delete(GenreEntity entity);

  void deleteAll();
}
