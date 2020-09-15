package ru.otus.hw14.service;

import lombok.NonNull;
import ru.otus.hw14.model.entity.AuthorEntity;

import java.util.Date;
import java.util.Optional;

public interface AuthorCrudService {

  Optional<AuthorEntity> findById(String id);

  Optional<AuthorEntity> findByFirstNameAndLastNameAndBirthday(String firstName, String lastName, @NonNull Date birthday);

  Iterable<AuthorEntity> findAll();

  AuthorEntity save(AuthorEntity entity);

  void delete(AuthorEntity entity);

  void deleteAll();
}
