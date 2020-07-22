package ru.otus.hw08.core.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.hw08.core.models.Author;

public interface AuthorRepository extends MongoRepository<Author, String>, CrudRepository<Author, String> {

}
