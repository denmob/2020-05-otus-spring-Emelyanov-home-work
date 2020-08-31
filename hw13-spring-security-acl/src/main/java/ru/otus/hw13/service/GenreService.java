package ru.otus.hw13.service;

import ru.otus.hw13.model.Genre;

import java.util.List;

public interface GenreService {

  Genre save(Genre entity);

  List<Genre> findAll();
}
