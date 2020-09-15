package ru.otus.hw14.service;

import ru.otus.hw14.model.entity.BookEntity;

import java.util.Date;
import java.util.Optional;

public interface BookCrudService {

  Optional<BookEntity> findById(String id);

  Optional<BookEntity> findByTitleAndDate(String title, Date date);

  Iterable<BookEntity> findAll();

  BookEntity save(BookEntity entity);

  void delete(BookEntity entity);

  void deleteAll();
}
