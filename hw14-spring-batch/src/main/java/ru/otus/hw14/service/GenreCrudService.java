package ru.otus.hw14.service;

import ru.otus.hw14.model.entity.GenreEntity;

import java.util.List;
import java.util.Optional;

public interface GenreCrudService {

  Optional<GenreEntity> findById(String id);

  Optional<GenreEntity> findByName(String name);

  List<GenreEntity> findAll();

  GenreEntity save(GenreEntity entity);

  void delete(GenreEntity entity);

  void deleteAll();
}
