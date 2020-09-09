package ru.otus.hw14.service;

import ru.otus.hw14.model.table.Comment;

import java.util.Optional;

public interface CommentCrudService {

  Optional<Comment> findById(String id);

  Iterable<Comment> findAll();

  Comment save(Comment entity);

  void delete(Comment entity);

  void deleteAll();
}
