package ru.otus.hw14.service;

import ru.otus.hw14.model.document.AuthorDocument;

import java.util.List;
import java.util.Optional;

public interface AuthorMongoService {

  Optional<AuthorDocument> findById(String id);

  List<AuthorDocument> findAll();

  AuthorDocument save(AuthorDocument entity);

  void delete(AuthorDocument entity);

  void deleteAll();
}
