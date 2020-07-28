package ru.otus.hw09.service;

import ru.otus.hw09.model.Author;

import java.util.Optional;

public interface AuthorService {

  Author save(Author entity);

  Optional<Author> readLastNameEquals(String id);

  Author update(Author entity);

  boolean delete(String id);
}
