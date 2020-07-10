package ru.otus.hw06.core.repositories;

import ru.otus.hw06.core.dto.BookWithComments;
import ru.otus.hw06.core.models.Book;

import java.util.Optional;

public interface BookRepositoryJpa extends GenericRepositoryJpa<Book> {
  Optional<BookWithComments> getByIdWithComments(long id);
}
