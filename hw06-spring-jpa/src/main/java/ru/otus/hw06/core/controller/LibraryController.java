package ru.otus.hw06.core.controller;

public interface LibraryController {

  String createBook(String title, String date, long authorId, long genreId);

  String readBook(long id);

  String updateBook(long id, String title, String date, long authorId, long genreId);

  String deleteBook(long id);

  void printTableBooks();

  void printTableAuthors();

  void printTableGenres();

  void printTableComments();
}
