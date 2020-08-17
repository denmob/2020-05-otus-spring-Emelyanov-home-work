package ru.otus.hw11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Book;
import ru.otus.hw11.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Override
  @Transactional
  public Mono<Book> save(Book entity) {
    return bookRepository.save(entity);
  }

  @Override
  public Mono<Book> readBookById(String bookId) {
    return bookRepository.findById(bookId);
  }

  @Override
  public Flux<Book> getLastAddedBooks(int count) {
    // to do
    return bookRepository.findAll();
  }

  @Override
  public Mono<Void> deleteBookById(String bookId) {
    return bookRepository.deleteBookById(bookId);
  }
}
