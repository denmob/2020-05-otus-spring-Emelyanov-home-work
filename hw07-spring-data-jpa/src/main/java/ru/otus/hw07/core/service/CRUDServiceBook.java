package ru.otus.hw07.core.service;

import ru.otus.hw07.core.dto.BookWithComments;
import ru.otus.hw07.core.models.Book;

import java.util.Optional;

public interface CRUDServiceBook extends CRUDServiceGeneric<Book> {
  Optional<BookWithComments> readWithComments(long id);
}
