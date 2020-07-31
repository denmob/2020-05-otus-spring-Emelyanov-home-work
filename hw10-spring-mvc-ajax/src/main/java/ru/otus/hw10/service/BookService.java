package ru.otus.hw10.service;

import org.springframework.data.domain.Page;
import ru.otus.hw10.model.Book;


import java.util.Optional;

public interface BookService {

  Book save(Book entity);

  Optional<Book> readBookById(String bookId);

  boolean deleteBookById(String bookId);

  Page<Book> getLastAddedBooks(int count);
}
