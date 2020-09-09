package ru.otus.hw14.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw14.model.document.Genre;

import java.util.Optional;

@Repository
public interface GenreMongoRepository extends MongoRepository<Genre,String>{
}
