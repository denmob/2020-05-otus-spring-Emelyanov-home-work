package ru.otus.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Book;

import java.util.Date;

public interface BookRepository extends ReactiveMongoRepository<Book,String> {

  Mono<Book> findByAuthorLastNameEquals(String authorLastName);

  Flux<Book> findByTitleContainsOrDateEquals(String bookTitle, Date bookDate);

  Flux<Book> findAll();

  Mono<Void> deleteBookByTitleEquals(String bookTitle);

  Mono<Void> deleteBookById(String bookId);
}
