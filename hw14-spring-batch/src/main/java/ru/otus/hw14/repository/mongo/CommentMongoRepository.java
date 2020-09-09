package ru.otus.hw14.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw14.model.document.Comment;

import java.util.List;

@Repository
public interface CommentMongoRepository extends MongoRepository<Comment,String>{

  List<Comment> findAllByBookId(String bookId);

  Long deleteCommentAllByBookId(String bookId);
}
