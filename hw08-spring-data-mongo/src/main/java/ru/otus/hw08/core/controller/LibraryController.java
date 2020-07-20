package ru.otus.hw08.core.controller;

public interface LibraryController {
  String createBook();

  String readBook(String id);

  String updateBookTitle(String bookId);

  String updateBookDate(String bookId);

  String updateBookGenre(String bookId);

  String updateBookAuthor(String bookId);

  String deleteBook(String id);

  String createComment(String bookId, String commentary);

  String readComment(String id);

  String updateComment(String id, String commentary);

  String deleteComment(String id);

  void printTableBooks();

  void printTableAuthors();

  void printTableGenres();

  void printTableComments();
}
