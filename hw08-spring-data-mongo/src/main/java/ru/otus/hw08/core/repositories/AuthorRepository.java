package ru.otus.hw08.core.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.hw08.core.models.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String>, CrudRepository<Author, String> {

  Optional<Author> findByLastNameEquals(String authorLastName);

  boolean deleteAuthorByLastNameEquals(String authorLastName);
}
