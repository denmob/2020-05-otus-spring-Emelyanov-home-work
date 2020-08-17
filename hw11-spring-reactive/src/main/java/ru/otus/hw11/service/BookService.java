package ru.otus.hw11.service;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Book;


public interface BookService {

  Mono<Book> save(Book entity);

  Mono<Book> readBookById(String bookId);

  Mono<Void> deleteBookById(String bookId);

  Flux<Book> getLastAddedBooks(int count);
}
