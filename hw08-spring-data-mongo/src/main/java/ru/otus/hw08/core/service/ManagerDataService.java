package ru.otus.hw08.core.service;

import ru.otus.hw08.core.dto.BookWithComments;
import ru.otus.hw08.core.models.Author;
import ru.otus.hw08.core.models.Book;
import ru.otus.hw08.core.models.Comment;
import ru.otus.hw08.core.models.Genre;

import java.util.List;
import java.util.Optional;

public interface ManagerDataService {
  boolean createBook(Book book);

  Optional<BookWithComments> readBookByTitle(String bookTitle);

  boolean deleteBookByTitle(String bookTitle);

  boolean createComment(String bookTitle, String commentary);

  List<Comment> readComments(String partComment);

  boolean deleteComment(String partComment);

  void printTableBooks();

  void printTableAuthors();

  void printTableGenres();

  void printTableComments();

  Optional<Author> readAuthorByLastName(String lastName);

  Optional<Genre> readGenreByName(String name);
}
