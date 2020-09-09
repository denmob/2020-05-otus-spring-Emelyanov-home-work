package ru.otus.hw14.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw14.model.document.Book;

@Repository
public interface BookMongoRepository extends MongoRepository<Book,String>{
}
