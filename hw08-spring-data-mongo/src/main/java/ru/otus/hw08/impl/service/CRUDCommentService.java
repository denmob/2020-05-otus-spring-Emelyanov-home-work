package ru.otus.hw08.impl.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw08.core.models.Comment;
import ru.otus.hw08.core.repositories.CommentRepository;
import ru.otus.hw08.core.service.CRUDServiceComment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDCommentService implements CRUDServiceComment {

  private final CommentRepository commentRepository;

  @Override
  @Transactional
  public Comment create(Comment entity) {
    return commentRepository.save(entity);
  }

  @Override
  public List<Comment> readCommentaryContains(String partComment) {
    return commentRepository.findCommentByCommentaryContains(partComment);
  }

  @Override
  public List<Comment> readAllForBook(String bookId) {
    return commentRepository.findAllByBookId(bookId);
  }

  @Override
  @Transactional
  public Comment update(Comment entity) {
    return commentRepository.save(entity);
  }

  @Override
  @Transactional
  public boolean deleteCommentaryContains(String partComment) {
    return (commentRepository.deleteCommentByCommentaryContains(partComment)==1L);
  }
}
