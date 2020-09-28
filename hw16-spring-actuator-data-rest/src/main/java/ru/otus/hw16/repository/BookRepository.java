package ru.otus.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.hw16.model.Book;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "book", path = "book")
public interface BookRepository extends MongoRepository<Book,String>{

  @RestResource(path = "find-all", rel = "find-all")
  List<Book> findAll();

  @RestResource(path = "find-by-author-lastName", rel = "find-by-author-lastName")
  Optional<Book> findByAuthorLastNameEquals(String authorLastName);

  @RestResource(path = "delete-by-title", rel = "delete-by-title")
  Long deleteBookByTitleEquals(String bookTitle);

  @RestResource(path = "delete-by-id", rel = "delete-by-id")
  Long deleteBookById(String bookId);
}
