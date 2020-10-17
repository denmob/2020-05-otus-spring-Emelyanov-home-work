package ru.otus.web.service;

import ru.otus.library.model.dto.CommentDto;

import java.util.List;

public interface CommentService {

  List<CommentDto> getComments(String id);
}
