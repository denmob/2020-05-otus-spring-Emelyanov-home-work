package ru.otus.hw08.core.service;

import ru.otus.hw08.core.dto.BookWithComments;
import ru.otus.hw08.core.models.Book;
import java.util.Optional;

public interface CRUDServiceBook {

  Book create(Book entity);

  Optional<Book> readByTitleContains(String id);

  Optional<BookWithComments> readWithComments(String bookTitle);

  Book update(Book entity);

  boolean deleteByTitleEquals(String id);
}
