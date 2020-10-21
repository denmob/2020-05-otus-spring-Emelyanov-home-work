package ru.otus.book.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.model.Book;

import java.util.Optional;

@SuppressWarnings("unused")
public interface BookRepository extends MongoRepository<Book, String> {

  Optional<Book> findByAuthorLastNameEquals(String authorLastName);

  Optional<Book> findByTitleContains(String bookTitle);

  Long deleteBookByTitleEquals(String bookTitle);

  Long deleteBookById(String bookId);
}
