package ru.otus.hw14.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw14.model.document.Author;

@Repository
public interface AuthorMongoRepository extends MongoRepository<Author, String> {
}