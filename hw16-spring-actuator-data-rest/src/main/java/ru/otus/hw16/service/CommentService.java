package ru.otus.hw16.service;

import ru.otus.hw16.model.Comment;

import java.util.List;

public interface CommentService {

  Comment save(Comment entity);

  List<Comment> readAllForBook(String bookId);
}
