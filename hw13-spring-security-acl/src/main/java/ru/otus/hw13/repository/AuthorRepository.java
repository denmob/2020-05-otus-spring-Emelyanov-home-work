package ru.otus.hw13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw13.model.Author;

import java.util.Optional;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {

  Optional<Author> findByLastNameEquals(String authorLastName);

  Long deleteAuthorByLastNameEquals(String authorLastName);

  Long deleteAuthorById(String authorId);
}
