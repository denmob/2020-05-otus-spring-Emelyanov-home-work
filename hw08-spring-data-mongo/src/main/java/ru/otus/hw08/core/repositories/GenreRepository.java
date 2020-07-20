package ru.otus.hw08.core.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.core.models.Genre;

public interface GenreRepository extends MongoRepository<Genre,String> {
}
