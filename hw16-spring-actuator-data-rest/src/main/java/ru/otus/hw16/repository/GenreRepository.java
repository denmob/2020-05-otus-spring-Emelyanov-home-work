package ru.otus.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.hw16.model.Genre;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "genre", path = "genre")
public interface GenreRepository extends MongoRepository<Genre,String>{

  @RestResource(path = "find-all", rel = "find-all")
  List<Genre> findAll();

  @RestResource(path = "find-by-name", rel = "find-by-name")
  Optional<Genre> findByNameEquals(String genreName);

  @RestResource(path = "delete-by-name", rel = "delete-by-name")
  Long deleteByNameEquals(String genreName);
}
