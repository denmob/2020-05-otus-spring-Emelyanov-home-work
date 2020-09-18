package ru.otus.hw14.service;

import ru.otus.hw14.model.document.GenreDocument;

import java.util.List;
import java.util.Optional;

public interface GenreMongoService {

  Optional<GenreDocument> findById(String id);

  List<GenreDocument> findAll();

  GenreDocument save(GenreDocument entity);

  void delete(GenreDocument entity);

  void deleteAll();
}
