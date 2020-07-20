package ru.otus.hw08.core.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.core.models.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
