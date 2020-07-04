package ru.otus.hw06.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw06.core.models.Comment;
import ru.otus.hw06.core.repositories.CommentRepositoryJpa;
import ru.otus.hw06.core.service.CRUDService;
import ru.otus.hw06.impl.repositories.BookRepositoryJpaImpl;

@Service
@RequiredArgsConstructor
public class CRUDCommentService implements CRUDService<Comment> {

  private final CommentRepositoryJpa commentRepositoryJpa;

  @Override
  public Comment create(Comment entity) {
    return null;
  }

  @Override
  public Comment read(Comment entity) {
    return null;
  }

  @Override
  public boolean delete(long id) {
    return false;
  }

  @Override
  public Comment update(Comment entity) {
    return null;
  }
}
