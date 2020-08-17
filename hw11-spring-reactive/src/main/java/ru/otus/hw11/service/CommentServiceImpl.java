package ru.otus.hw11.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Comment;
import ru.otus.hw11.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  @Override
  @Transactional
  public Mono<Comment> save(Comment entity) {
    return commentRepository.save(entity);
  }

  @Override
  public Flux<Comment> readCommentaryContains(String partComment) {
    return commentRepository.findCommentByCommentaryContains(partComment);
  }

  @Override
  public Flux<Comment> readAllForBook(String bookId) {
    return commentRepository.findAllByBookId(bookId);
  }

  @Override
  @Transactional
  public Mono<Void> deleteCommentaryContains(String partComment) {
    return (commentRepository.deleteCommentByCommentaryContains(partComment));
  }

  @Override
  public Mono<Void> deleteCommentAllByBookId(String bookId) {
    return commentRepository.deleteCommentAllByBookId(bookId);
  }
}
