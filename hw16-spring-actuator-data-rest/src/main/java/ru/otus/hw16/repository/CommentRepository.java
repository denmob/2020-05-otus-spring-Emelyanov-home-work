package ru.otus.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.hw16.model.Comment;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "comment", path = "comment")
public interface CommentRepository extends MongoRepository<Comment,String>{

  @RestResource(path = "find-all-by-bookId", rel = "find-all-by-bookId")
  List<Comment> findAllByBookId(String bookId);

  @RestResource(path = "find-all-by-commentary", rel = "find-all-by-commentary")
  List<Comment> findCommentByCommentaryContains(String partCommentary);

  @RestResource(path = "delete-all-by-bookId", rel = "delete-all-by-bookId")
  Long deleteCommentAllByBookId(String bookId);

  @RestResource(path = "delete-by-id", rel = "delete-by-id")
  Long deleteCommentById(String commentId);
}
