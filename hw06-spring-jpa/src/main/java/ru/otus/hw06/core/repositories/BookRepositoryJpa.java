package ru.otus.hw06.core.repositories;

import ru.otus.hw06.core.dto.BookWithComments;
import ru.otus.hw06.core.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {
  long count();

  Book insert(Book book);

  Optional<Book> getById(long id);

  Optional<BookWithComments> getByIdWithComments(long id);

  List<Book> getAll();

  boolean deleteById(long id);
}
