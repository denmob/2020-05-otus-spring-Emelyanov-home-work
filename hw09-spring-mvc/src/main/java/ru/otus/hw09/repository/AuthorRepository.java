package ru.otus.hw09.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw09.model.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

  Optional<Author> findByLastNameEquals(String authorLastName);

  Long deleteAuthorByLastNameEquals(String authorLastName);

  Long deleteAuthorById(String authorId);
}
