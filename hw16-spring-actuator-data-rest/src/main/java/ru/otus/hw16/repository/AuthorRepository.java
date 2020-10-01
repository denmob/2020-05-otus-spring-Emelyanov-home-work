package ru.otus.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.hw16.model.Author;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "author", path = "author")
public interface AuthorRepository extends MongoRepository<Author, String> {

  @RestResource(path = "find-all", rel = "find-all")
  List<Author> findAll();

  @RestResource(path = "find-by-lastName", rel = "find-by-lastName")
  Optional<Author> findByLastNameEquals(String authorLastName);

  @RestResource(path = "delete-by-lastName", rel = "delete-by-lastName")
  Long deleteAuthorByLastNameEquals(String authorLastName);

  @RestResource(path = "delete-by-id", rel = "delete-by-id")
  Long deleteAuthorById(String authorId);
}
