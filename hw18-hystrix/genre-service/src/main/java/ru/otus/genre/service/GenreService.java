package ru.otus.genre.service;

import ru.otus.library.model.Genre;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface GenreService {

  Genre save(Genre entity);

  Optional<Genre> findByNameEquals(String id);

  Optional<Genre> findById(String genreId);

  boolean deleteByNameEquals(String name);

  List<Genre> findAll();
}
