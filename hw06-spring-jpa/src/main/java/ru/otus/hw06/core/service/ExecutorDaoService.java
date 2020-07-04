package ru.otus.hw06.core.service;

import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Genre;

public interface ExecutorDaoService {

  boolean insertBook(Book book);

  boolean deleteBook(long id);

  boolean insertAuthor(Author author);

  boolean deleteAuthor(long id);

  boolean insertGenre(Genre genre);

  boolean deleteGenre(long id);


}
