package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.entity.BookEntity;
import ru.otus.hw14.repository.crud.BookCrudRepository;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCrudServiceImpl implements BookCrudService {

  private final BookCrudRepository bookCrudRepository;

  @Override
  @Transactional
  public BookEntity save(BookEntity entity) {
    return bookCrudRepository.save(entity);
  }

  @Override
  public Optional<BookEntity> findById(String id) {
    return bookCrudRepository.findById(Long.valueOf(id));
  }

  @Override
  public Optional<BookEntity> findByTitleAndDate(String title, Date date) {
    return bookCrudRepository.findByTitleAndDate(title,date);
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<BookEntity> findAll() {
    return bookCrudRepository.findAll();
  }

  @Override
  @Transactional
  public void delete(BookEntity entity) {
    bookCrudRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    bookCrudRepository.deleteAll();
  }
}
