package ru.otus.hw06.core.service;

import ru.otus.hw06.core.dto.BookWithComments;
import ru.otus.hw06.core.models.Book;

import java.util.Optional;

public interface CRUDServiceBook {

  Book create(Book entity);

  Optional<Book> read(long id);

  boolean delete(long id);

  Book update(Book entity);

  Optional<BookWithComments> readWithComments(long id);
}
