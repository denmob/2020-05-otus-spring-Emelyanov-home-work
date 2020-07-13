package ru.otus.hw06.core.service;

import ru.otus.hw06.core.models.Comment;

import java.util.Optional;

public interface CRUDServiceComment {
  Comment create(Comment entity);

  Optional<Comment> read(long id);

  Comment update(Comment entity);
}
