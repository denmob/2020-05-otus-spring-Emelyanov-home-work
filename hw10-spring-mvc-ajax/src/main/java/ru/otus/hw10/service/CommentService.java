package ru.otus.hw10.service;

import ru.otus.hw10.model.Comment;

import java.util.List;

public interface CommentService {
  Comment save(Comment entity);

  List<Comment> readCommentaryContains(String partComment);

  List<Comment> readAllForBook(String bookId);

  boolean deleteCommentaryContains(String partComment);

  boolean deleteCommentAllByBookId(String bookId);
}
