package ru.otus.hw14.service;

import ru.otus.hw14.model.document.Comment;

import java.util.Optional;

public interface CommentMongoService {

  Optional<Comment> findById(String id);

  Iterable<Comment> findAll();

  Comment save(Comment entity);

  void delete(Comment entity);

  void deleteAll();

  Iterable<Comment> readAllForBook(String bookId);

  void deleteCommentAllByBookId(String bookId);
}
