package ru.otus.hw14.service;

import ru.otus.hw14.model.document.BookDocument;

import java.util.List;
import java.util.Optional;

public interface BookMongoService {

  Optional<BookDocument> findById(String id);

  List<BookDocument> findAll();

  BookDocument save(BookDocument entity);

  void delete(BookDocument entity);

  void deleteAll();
}
