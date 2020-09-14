package ru.otus.hw14.service;

import lombok.NonNull;
import ru.otus.hw14.model.table.Author;

import java.util.Date;
import java.util.Optional;

public interface AuthorCrudService {

  Optional<Author> findById(String id);

  Optional<Author> findByFirstNameAndLastNameAndBirthday(String firstName, String lastName, @NonNull Date birthday);

  Iterable<Author> findAll();

  Author save(Author entity);

  void delete(Author entity);

  void deleteAll();
}
