package ru.otus.book.service;

import ru.otus.library.model.dto.GenreDto;

public interface DefaultGenreService {

  GenreDto getGenreByName(String name);
}
