package ru.otus.hw06.core.repositories;

import ru.otus.hw06.core.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryJpa {
  long count();

  Comment insert(Comment entity);

  Optional<Comment> getById(long id);

  List<Comment> getAll();

  boolean deleteById(long id);
}
