package ru.otus.hw05.core.controller;

public interface LibraryController {

  String insertBook(String title, String date, long authorId, long genreId);

  String deleteBook(long id);

  String insertAuthor(String firstName, String lastName, String birthday);

  String deleteAuthor(long id);

  String insertGenre(String name);

  String deleteGenre(long id);

  void printTableBooks();

  void printTableAuthors();

  void printTableGenres();
}
