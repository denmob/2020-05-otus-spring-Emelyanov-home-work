package ru.otus.hw08.core.service;

import ru.otus.hw08.core.dto.BookWithComments;
import ru.otus.hw08.core.models.Book;

import java.util.Optional;

public interface CRUDServiceBook extends CRUDServiceGeneric<Book> {
  Optional<BookWithComments> readWithComments(String id);
}
