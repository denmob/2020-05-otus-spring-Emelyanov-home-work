package ru.otus.hw12.service;

import ru.otus.hw12.model.Comment;

import java.util.List;

public interface CommentService {
  Comment save(Comment entity);

  List<Comment> readAllForBook(String bookId);

  boolean deleteCommentAllByBookId(String bookId);
}
