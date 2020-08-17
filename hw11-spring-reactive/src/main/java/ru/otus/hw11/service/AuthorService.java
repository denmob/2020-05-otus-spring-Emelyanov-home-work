package ru.otus.hw11.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Author;

public interface AuthorService {

  Mono<Author> save(Author entity);

  Mono<Author> findByLastNameEquals(String authorLastName);

  Mono<Author> findById(String authorId);

  Mono<Void> deleteAuthorByLastNameEquals(String authorLastName);

  Mono<Void> deleteAuthorById(String authorId);

  Flux<Author> findAll();
}
