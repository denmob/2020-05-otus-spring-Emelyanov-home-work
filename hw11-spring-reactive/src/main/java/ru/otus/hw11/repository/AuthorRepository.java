package ru.otus.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw11.model.Author;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
