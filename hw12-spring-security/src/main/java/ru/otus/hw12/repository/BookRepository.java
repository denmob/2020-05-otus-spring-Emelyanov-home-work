package ru.otus.hw12.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw12.model.Book;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book,String>{

  Optional<Book> findByTitleContains(String bookTitle);

  Optional<Book> findByAuthorLastNameEquals(String authorLastName);

  List<Book> findByTitleContainsOrDateEquals(String bookTitle, Date bookDate);

  Long deleteBookByTitleEquals(String bookTitle);

  Long deleteBookById(String bookId);
}
