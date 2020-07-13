package ru.otus.hw06.core.controller;

public interface LibraryController {
  String createBook();

  String readBook(long id);

  String updateBookTitle(long bookId);

  String updateBookDate(long bookId);

  String updateBookGenre(long bookId);

  String updateBookAuthor(long bookId);

  String deleteBook(long id);

  String createComment(long bookId, String commentary);

  String readComment(long id);

  String updateComment(long id, String commentary);

  void printTableBooks();

  void printTableAuthors();

  void printTableGenres();

  void printTableComments();
}
