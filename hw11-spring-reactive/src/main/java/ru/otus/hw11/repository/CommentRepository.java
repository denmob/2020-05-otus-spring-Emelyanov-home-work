package ru.otus.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment,String> {

  Flux<Comment> findAllByBookId(String bookId);

  Flux<Comment> findCommentByCommentaryContains(String partComment);

  Mono<Void> deleteCommentAllByBookId(String bookId);

  Mono<Void> deleteCommentByCommentaryContains(String partComment);

  Mono<Void> deleteCommentById(String commentId);
}
