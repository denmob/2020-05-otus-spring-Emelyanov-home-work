package ru.otus.hw16.service;

import ru.otus.hw16.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

  Genre save(Genre entity);

  Optional<Genre> findById(String genreId);

  List<Genre> findAll();
}
