package ru.otus.hw16.service;

import ru.otus.hw16.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

  Author save(Author entity);

  Optional<Author> findById(String authorId);

  List<Author> findAll();
}
