package ru.otus.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre,String> {

  Mono<Genre> findByNameEquals(String genreName);

  Mono<Void> deleteByNameEquals(String genreName);
}
