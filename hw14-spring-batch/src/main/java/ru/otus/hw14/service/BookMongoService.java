package ru.otus.hw14.service;

import ru.otus.hw14.model.document.Book;

import java.util.Optional;

public interface BookMongoService {

  Optional<Book> findById(String id);

  Iterable<Book> findAll();

  Book save(Book entity);

  void delete(Book entity);

  void deleteAll();
}
