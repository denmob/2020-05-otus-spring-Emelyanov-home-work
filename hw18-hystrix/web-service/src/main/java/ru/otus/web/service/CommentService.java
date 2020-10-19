package ru.otus.web.service;

import ru.otus.library.model.Comment;
import ru.otus.library.model.dto.CommentDto;

import java.util.List;

public interface CommentService {

  List<Comment> getComments(String id);
}
