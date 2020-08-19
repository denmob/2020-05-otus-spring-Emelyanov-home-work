package ru.otus.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book,String> {

  Flux<Book> findAll();

  Mono<Void> deleteBookById(String bookId);
}
