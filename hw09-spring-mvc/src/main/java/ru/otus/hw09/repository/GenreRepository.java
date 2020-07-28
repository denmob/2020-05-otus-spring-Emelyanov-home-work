package ru.otus.hw09.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw09.model.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre,String>{

  Optional<Genre> findByNameEquals(String genreName);

  Long deleteByNameEquals(String genreName);

  Long deleteGenreById(String genreId);
}
