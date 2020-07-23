package ru.otus.hw08.core.service;

import ru.otus.hw08.core.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CRUDServiceComment {
  Comment create(Comment entity);

  Optional<Comment> read(String id);

  List<Comment> readAllForBook(String bookId);

  Comment update(Comment entity);

  boolean delete(String id);
}
