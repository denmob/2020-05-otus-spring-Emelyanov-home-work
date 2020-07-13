package ru.otus.hw06.core.repositories;

import ru.otus.hw06.core.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {
  long count();

  Book insert(Book entity);

  Optional<Book> getById(long id);

  List<Book> getAll();

  boolean deleteById(long id);
}
