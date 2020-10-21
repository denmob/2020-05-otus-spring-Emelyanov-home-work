package ru.otus.comment.service;

import ru.otus.library.model.Comment;

import java.util.List;

@SuppressWarnings("unused")
public interface CommentService {

  Comment save(Comment entity);

  List<Comment> readCommentaryContains(String partComment);

  List<Comment> readAllForBook(String bookId);

  boolean deleteCommentaryContains(String partComment);

  boolean deleteCommentAllByBookId(String bookId);
}
