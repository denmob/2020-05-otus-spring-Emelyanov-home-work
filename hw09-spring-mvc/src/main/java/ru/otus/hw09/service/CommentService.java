package ru.otus.hw09.service;

import ru.otus.hw09.model.Comment;

import java.util.List;

public interface CommentService {
  Comment create(Comment entity);

  List<Comment> readCommentaryContains(String partComment);

  List<Comment> readAllForBook(String bookId);

  Comment update(Comment entity);

  boolean deleteCommentaryContains(String id);
}
