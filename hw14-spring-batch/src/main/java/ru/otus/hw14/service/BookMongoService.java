package ru.otus.hw14.service;

import ru.otus.hw14.model.document.BookDocument;

import java.util.Optional;

public interface BookMongoService {

  Optional<BookDocument> findById(String id);

  Iterable<BookDocument> findAll();

  BookDocument save(BookDocument entity);

  void delete(BookDocument entity);

  void deleteAll();
}
