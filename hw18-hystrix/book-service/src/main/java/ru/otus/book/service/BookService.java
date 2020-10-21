package ru.otus.book.service;

import org.springframework.data.domain.Page;
import ru.otus.library.model.Book;

import java.util.Optional;

public interface BookService {

  Book save(Book entity);

  Optional<Book> readBookById(String bookId);

  Optional<Book> readBookByTitle(String bookTitle);

  boolean deleteBookById(String bookId);

  Page<Book> getLastAddedBooks(int count);
}
