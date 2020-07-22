package ru.otus.hw08.core.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.hw08.core.models.Book;

public interface BookRepository extends MongoRepository<Book,String>, CrudRepository<Book,String> {
}
