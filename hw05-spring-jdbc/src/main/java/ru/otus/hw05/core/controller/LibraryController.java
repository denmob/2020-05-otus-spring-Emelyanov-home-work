package ru.otus.hw05.core.controller;


public interface LibraryController {

  String insertBook(String title, String date, long authorId, long genreId);

  void printTableBooks();

  void printTableAuthors();

  void printTableGenres();
}
