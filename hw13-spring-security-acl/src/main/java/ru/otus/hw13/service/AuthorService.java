package ru.otus.hw13.service;

import ru.otus.hw13.model.Author;

import java.util.List;

public interface AuthorService {

  Author save(Author entity);

  List<Author> findAll();
}
