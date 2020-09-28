package ru.otus.hw16.service;

import ru.otus.hw16.model.Comment;

import java.util.List;

public interface CommentService {

  Comment save(Comment entity);

  List<Comment> readCommentaryContains(String partComment);

  List<Comment> readAllForBook(String bookId);

  boolean deleteCommentAllByBookId(String bookId);
}
