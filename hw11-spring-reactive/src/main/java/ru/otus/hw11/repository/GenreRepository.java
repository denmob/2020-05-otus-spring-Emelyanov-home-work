package ru.otus.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw11.model.Genre;

@Repository
public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
