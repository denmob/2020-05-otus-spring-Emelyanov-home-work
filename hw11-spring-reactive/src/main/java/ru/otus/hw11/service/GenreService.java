package ru.otus.hw11.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Genre;

public interface GenreService {

  Mono<Genre> save(Genre entity);

  Mono<Genre> findByNameEquals(String id);

  Mono<Genre> findById(String genreId);

  Mono<Void> deleteByNameEquals(String name);

  Flux<Genre> findAll();
}
