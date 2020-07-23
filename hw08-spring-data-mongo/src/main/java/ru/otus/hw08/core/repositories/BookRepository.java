package ru.otus.hw08.core.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.core.models.Book;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book,String>{

  Optional<Book> findByAuthorLastNameEquals(String authorLastName);

  List<Book> findByTitleContainsOrDateEquals(String bookTitle, Date bookDate);

  Long deleteBookByTitleEquals(String bookTitle);

  Long deleteBookById(String bookId);
}
