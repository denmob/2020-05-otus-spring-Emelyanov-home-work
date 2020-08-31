package ru.otus.hw13.service;

import ru.otus.hw13.model.Comment;

import java.util.List;

public interface CommentService {
  Comment save(Comment entity);

  List<Comment> readAllForBook(String bookId);

  boolean deleteCommentAllByBookId(String bookId);
}
