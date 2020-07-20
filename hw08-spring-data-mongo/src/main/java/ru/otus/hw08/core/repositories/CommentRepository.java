package ru.otus.hw08.core.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.core.models.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String> {

  List<Comment> getAllByBookId(String bookId);
}
