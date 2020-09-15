package ru.otus.hw14.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw14.model.document.BookDocument;

@Repository
public interface BookMongoRepository extends MongoRepository<BookDocument,String>{
}
