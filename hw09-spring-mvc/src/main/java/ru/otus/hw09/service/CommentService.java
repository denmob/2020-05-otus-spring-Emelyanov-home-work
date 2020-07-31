package ru.otus.hw09.service;

import ru.otus.hw09.model.Comment;

import java.util.List;

public interface CommentService {
  Comment save(Comment entity);

  List<Comment> readCommentaryContains(String partComment);

  List<Comment> readAllForBook(String bookId);

  boolean deleteCommentaryContains(String partComment);

  boolean deleteCommentAllByBookId(String bookId);
}
