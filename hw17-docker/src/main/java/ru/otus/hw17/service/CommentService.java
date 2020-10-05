package ru.otus.hw17.service;

import ru.otus.hw17.model.Comment;

import java.util.List;

public interface CommentService {

  Comment save(Comment entity);

  List<Comment> readAllForBook(String bookId);
}
