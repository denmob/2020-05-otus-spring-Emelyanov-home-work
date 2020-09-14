package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.table.Book;
import ru.otus.hw14.model.table.BookWithComments;
import ru.otus.hw14.repository.crud.BookCrudRepository;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCrudServiceImpl implements BookCrudService {

  private final BookCrudRepository bookCrudRepository;

  @Override
  @Transactional
  public Book save(Book entity) {
    return bookCrudRepository.save(entity);
  }

  @Override
  public Optional<Book> findById(String id) {
    return bookCrudRepository.findById(Long.valueOf(id));
  }

  @Override
  public Optional<Book> findByTitleAndDate(String title, Date date) {
    return bookCrudRepository.findByTitleAndDate(title,date);
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<Book> findAll() {
    return bookCrudRepository.findAll();
  }

  @Override
  @Transactional
  public void delete(Book entity) {
    bookCrudRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    bookCrudRepository.deleteAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<BookWithComments> readWithComments(long id) {
    Optional<Book> optionalBook = bookCrudRepository.findById(id);
    if (optionalBook.isPresent()) {
      Hibernate.initialize(optionalBook.get().getComments());
      return optionalBook.map(book -> new BookWithComments(book, book.getComments()));
    }
    return Optional.empty();
  }
}
