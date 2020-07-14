package ru.otus.hw07.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw07.core.models.Comment;
import ru.otus.hw07.core.repositories.CommentRepository;
import ru.otus.hw07.core.service.CRUDServiceComment;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDCommentService implements CRUDServiceComment {

  private final CommentRepository commentRepository;

  @Override
  public Comment create(Comment entity) {
    return commentRepository.save(entity);
  }

  @Override
  public Optional<Comment> read(long id) {
    return commentRepository.findById(id);
  }

  @Override
  public Comment update(Comment entity) {
    return commentRepository.save(entity);
  }

  @Override
  public void delete(long id) {
      commentRepository.deleteById(id);
  }
}
