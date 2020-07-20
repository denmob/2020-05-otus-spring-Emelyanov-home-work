package ru.otus.hw08.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw08.core.dto.BookWithComments;
import ru.otus.hw08.core.models.Book;
import ru.otus.hw08.core.repositories.BookRepository;
import ru.otus.hw08.core.service.CRUDServiceBook;

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
  public Optional<Book> read(String id) {
    return bookRepository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<BookWithComments> readWithComments(String id) {
    Optional<Book> optionalBook = read(id);
    if (optionalBook.isPresent()) {
      return optionalBook.map(book -> new BookWithComments(book, book.getComments()));
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public void delete(String id) {
    bookRepository.deleteById(id);
  }

  @Override
  @Transactional
  public Book update(Book entity) {
    return bookRepository.save(entity);
  }
}
