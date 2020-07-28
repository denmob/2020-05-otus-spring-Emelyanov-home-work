package ru.otus.hw09.service;

import org.springframework.data.domain.Page;
import ru.otus.hw09.dto.BookWithComments;
import ru.otus.hw09.model.Book;

import java.util.Optional;

public interface BookService {

  Book save(Book entity);

  Optional<Book> readBookById(String bookId);

  Optional<Book> readBookByTitleContains(String bookTitle);

  Optional<BookWithComments> readBookWithCommentsById(String bookId);

  Optional<BookWithComments> readBookWithCommentsByTitleContains(String bookTitle);

  Book update(Book entity);

  boolean deleteByTitleEquals(String id);

  Page<Book> getLastAddedBooks(int count);
}
