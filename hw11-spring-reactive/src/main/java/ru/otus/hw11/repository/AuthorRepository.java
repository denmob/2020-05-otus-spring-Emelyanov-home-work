package ru.otus.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

  Mono<Author> findByLastNameEquals(String authorLastName);

  Mono<Void> deleteAuthorByLastNameEquals(String authorLastName);

  Mono<Void> deleteAuthorById(String authorId);
}
