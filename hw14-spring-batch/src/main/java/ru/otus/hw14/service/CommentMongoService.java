package ru.otus.hw14.service;

import ru.otus.hw14.model.document.CommentDocument;

import java.util.List;
import java.util.Optional;

public interface CommentMongoService {

  Optional<CommentDocument> findById(String id);

  List<CommentDocument> findAll();

  CommentDocument save(CommentDocument entity);

  void delete(CommentDocument entity);

  void deleteAll();

  List<CommentDocument> readAllForBook(String bookId);

  void deleteCommentAllByBookId(String bookId);
}
