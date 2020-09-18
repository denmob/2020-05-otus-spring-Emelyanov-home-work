package ru.otus.hw14.service;

import ru.otus.hw14.model.entity.BookEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookCrudService {

  Optional<BookEntity> findById(String id);

  Optional<BookEntity> findByTitleAndDate(String title, Date date);

  List<BookEntity> findAll();

  BookEntity save(BookEntity entity);

  void delete(BookEntity entity);

  void deleteAll();
}
