package ru.otus.web.service;

import ru.otus.library.model.Genre;
import ru.otus.library.model.dto.GenreDto;

import java.util.List;

public interface GenreService {

  List<GenreDto> getGenres();

  GenreDto getGenreById(String id);
}
