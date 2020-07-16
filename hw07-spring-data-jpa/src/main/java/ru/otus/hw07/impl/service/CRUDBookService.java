package ru.otus.hw07.impl.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw07.core.dto.BookWithComments;
import ru.otus.hw07.core.models.Book;
import ru.otus.hw07.core.repositories.BookRepository;
import ru.otus.hw07.core.service.CRUDServiceBook;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDBookService implements CRUDServiceBook {

  private final BookRepository bookRepository;

  @Override
  @Transactional
  public Book create(Book entity) {
    return bookRepository.save(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Book> read(long id) {
    return bookRepository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<BookWithComments> readWithComments(long id) {
    Optional<Book> optionalBook = read(id);
    if (optionalBook.isPresent()) {
      Hibernate.initialize(optionalBook.get().getComments());
      return optionalBook.map(book -> new BookWithComments(book, book.getComments()));
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public void delete(long id) {
    bookRepository.deleteById(id);
  }

  @Override
  @Transactional
  public Book update(Book entity) {
    return bookRepository.save(entity);
  }
}
