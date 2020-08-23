package ru.otus.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

  Flux<Book> findAll();

  Mono<Long> deleteBookById(String bookId);
}
