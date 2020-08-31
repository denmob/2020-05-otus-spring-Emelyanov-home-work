package ru.otus.hw13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw13.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String>{

  List<Comment> findAllByBookId(String bookId);

  List<Comment> findCommentByCommentaryContains(String partComment);

  Long deleteCommentAllByBookId(String bookId);

  Long deleteCommentByCommentaryContains(String partComment);

  Long deleteCommentById(String commentId);
}
