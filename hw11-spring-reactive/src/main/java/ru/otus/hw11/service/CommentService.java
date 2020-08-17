package ru.otus.hw11.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Comment;

public interface CommentService {

  Mono<Comment> save(Comment entity);

  Flux<Comment> readCommentaryContains(String partComment);

  Flux<Comment> readAllForBook(String bookId);

  Mono<Void> deleteCommentaryContains(String partComment);

  Mono<Void> deleteCommentAllByBookId(String bookId);
}
