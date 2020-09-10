package ru.otus.hw14.service;

import ru.otus.hw14.model.table.BookWithComments;
import ru.otus.hw14.model.table.Book;

import java.util.Optional;

public interface BookCrudService {

  Optional<Book> findById(String id);

  Iterable<Book> findAll();

  Book save(Book entity);

  void delete(Book entity);

  void deleteAll();

  Optional<BookWithComments> readWithComments(long id);
}
