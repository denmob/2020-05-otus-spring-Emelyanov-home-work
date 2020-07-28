package ru.otus.hw09.service;

import ru.otus.hw09.dto.BookWithComments;
import ru.otus.hw09.model.Book;
import java.util.Optional;

public interface BookService {

  Book create(Book entity);

  Optional<Book> readByTitleContains(String id);

  Optional<BookWithComments> readWithComments(String bookTitle);

  Book update(Book entity);

  boolean deleteByTitleEquals(String id);
}
