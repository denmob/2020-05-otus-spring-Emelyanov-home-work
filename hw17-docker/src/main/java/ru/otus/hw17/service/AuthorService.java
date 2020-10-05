package ru.otus.hw17.service;

import ru.otus.hw17.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

  Author save(Author entity);

  Optional<Author> findById(String authorId);

  List<Author> findAll();
}
