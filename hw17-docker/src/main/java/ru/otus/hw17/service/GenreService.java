package ru.otus.hw17.service;

import ru.otus.hw17.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

  Genre save(Genre entity);

  Optional<Genre> findById(String genreId);

  List<Genre> findAll();
}
