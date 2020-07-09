package ru.otus.hw06.core.repositories;

import ru.otus.hw06.core.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryJpa {

  long count();

  Author insert(Author author);

  Optional<Author> getById(long id);

  List<Author> getAll();

  boolean deleteById(long id);

}
