package ru.otus.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.hw11.model.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment,String> {

  Flux<Comment> findAllByBookId(String bookId);
}
