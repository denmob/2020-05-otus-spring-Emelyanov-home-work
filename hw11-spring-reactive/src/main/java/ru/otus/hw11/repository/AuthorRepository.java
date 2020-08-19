package ru.otus.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.hw11.model.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
