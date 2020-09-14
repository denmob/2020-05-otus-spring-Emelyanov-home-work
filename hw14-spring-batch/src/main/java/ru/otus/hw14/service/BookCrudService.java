package ru.otus.hw14.service;

import ru.otus.hw14.model.table.Book;
import ru.otus.hw14.model.table.BookWithComments;

import java.util.Date;
import java.util.Optional;

public interface BookCrudService {

  Optional<Book> findById(String id);

  Optional<Book> findByTitleAndDate(String title, Date date);

  Iterable<Book> findAll();

  Book save(Book entity);

  void delete(Book entity);

  void deleteAll();

  Optional<BookWithComments> readWithComments(long id);
}
