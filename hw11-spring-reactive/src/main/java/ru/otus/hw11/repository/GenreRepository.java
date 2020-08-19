package ru.otus.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.hw11.model.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre,String> {
}
