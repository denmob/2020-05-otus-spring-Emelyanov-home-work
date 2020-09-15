package ru.otus.hw14.service;

import ru.otus.hw14.model.document.CommentDocument;

import java.util.Optional;

public interface CommentMongoService {

  Optional<CommentDocument> findById(String id);

  Iterable<CommentDocument> findAll();

  CommentDocument save(CommentDocument entity);

  void delete(CommentDocument entity);

  void deleteAll();

  Iterable<CommentDocument> readAllForBook(String bookId);

  void deleteCommentAllByBookId(String bookId);
}
