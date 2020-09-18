package ru.otus.hw14.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw14.model.document.GenreDocument;

@Repository
public interface GenreMongoRepository extends MongoRepository<GenreDocument,String>{
}
