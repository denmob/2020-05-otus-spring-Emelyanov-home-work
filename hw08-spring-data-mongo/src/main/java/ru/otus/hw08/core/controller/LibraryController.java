package ru.otus.hw08.core.controller;

public interface LibraryController {
  String createBook();

  String readBook(String id);

  String deleteBook(String id);

  String createComment(String bookId, String commentary);

  String readComment(String id);

  String deleteComment(String id);

  void printTableBooks();

  void printTableAuthors();

  void printTableGenres();

  void printTableComments();
}
