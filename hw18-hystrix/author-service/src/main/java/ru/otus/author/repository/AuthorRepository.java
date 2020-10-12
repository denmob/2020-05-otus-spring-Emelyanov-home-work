package ru.otus.author.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.model.Author;


import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

  Optional<Author> findByLastNameEquals(String authorLastName);

  Long deleteAuthorByLastNameEquals(String authorLastName);

  Long deleteAuthorById(String authorId);
}
