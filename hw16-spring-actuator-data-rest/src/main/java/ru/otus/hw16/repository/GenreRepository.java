package ru.otus.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw16.model.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre,String>{

  Optional<Genre> findByNameEquals(String genreName);

  Long deleteByNameEquals(String genreName);
}
