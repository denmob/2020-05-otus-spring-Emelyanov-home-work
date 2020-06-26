package ru.otus.hw05.core.service;

import ru.otus.hw05.core.domain.Author;
import ru.otus.hw05.core.domain.Book;
import ru.otus.hw05.core.domain.Genre;

public interface ExecutorDaoService {

  void insertBook(Book book);

  void deleteBook(long id);

  void insertAuthor(Author author);

  void deleteAuthor(long id);

  void insertGenre(Genre genre);

  void deleteGenre(long id);


}
