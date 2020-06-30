package ru.otus.hw05.core.service;

import ru.otus.hw05.core.domain.Author;
import ru.otus.hw05.core.domain.Book;
import ru.otus.hw05.core.domain.Genre;

public interface ExecutorDaoService {

  boolean insertBook(Book book);

  boolean deleteBook(long id);

  boolean insertAuthor(Author author);

  boolean deleteAuthor(long id);

  boolean insertGenre(Genre genre);

  boolean deleteGenre(long id);


}
