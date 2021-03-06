package ru.otus.hw13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw13.model.Book;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book,String>{

  Optional<Book> findByAuthorLastNameEquals(String authorLastName);

  List<Book> findByTitleContainsOrDateEquals(String bookTitle, Date bookDate);

  Long deleteBookByTitleEquals(String bookTitle);

  Long deleteBookById(String bookId);
}
