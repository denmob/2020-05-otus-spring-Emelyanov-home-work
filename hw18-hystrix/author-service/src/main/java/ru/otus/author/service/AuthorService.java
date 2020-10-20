package ru.otus.author.service;

import ru.otus.library.model.Author;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface AuthorService {

  Author save(Author entity);

  Optional<Author> findByLastNameEquals(String authorLastName);

  Optional<Author> findById(String authorId);

  boolean deleteAuthorByLastNameEquals(String authorLastName);

  boolean deleteAuthorById(String authorId);

  List<Author> findAll();
}
