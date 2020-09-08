package ru.otus.hw13.service;

import org.springframework.data.domain.Page;
import ru.otus.hw13.model.Book;

import java.util.Optional;

public interface BookService {

  Book save(Book entity);

  Optional<Book> readBookById(String bookId);

  boolean deleteBookById(String bookId);

  Page<Book> getLastAddedBooks(int count);
}
