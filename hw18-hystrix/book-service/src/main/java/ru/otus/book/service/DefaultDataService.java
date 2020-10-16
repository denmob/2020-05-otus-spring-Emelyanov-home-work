package ru.otus.book.service;

import ru.otus.library.model.Author;
import ru.otus.library.model.Genre;

public interface DefaultDataService {

  Author getAuthorByLastName(String lastName);

  Genre getGenreByName(String name);
}
