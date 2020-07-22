package ru.otus.hw08.core.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.hw08.core.models.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String>, CrudRepository<Comment,String> {

  List<Comment> findAllByBookId(String bookId);
}
