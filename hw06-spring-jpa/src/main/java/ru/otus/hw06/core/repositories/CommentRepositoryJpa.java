package ru.otus.hw06.core.repositories;

import ru.otus.hw06.core.models.Comment;

import java.util.List;

public interface CommentRepositoryJpa extends GenericRepositoryJpa<Comment> {

  List<Comment> getAllByBookId(long bookId);
}
