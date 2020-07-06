package ru.otus.hw06.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw06.core.models.Comment;
import ru.otus.hw06.core.repositories.CommentRepositoryJpa;
import ru.otus.hw06.core.service.CRUDService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDCommentService implements CRUDService<Comment> {

  private final CommentRepositoryJpa commentRepositoryJpa;

  @Override
  public Comment create(Comment entity) {
    return commentRepositoryJpa.insert(entity);
  }

  @Override
  public Optional<Comment> read(long id) {
    return commentRepositoryJpa.getById(id);
  }

  @Override
  public boolean delete(long id) {
    return commentRepositoryJpa.deleteById(id);
  }

  @Override
  public Comment update(Comment entity) {
    return commentRepositoryJpa.insert(entity);
  }
}
