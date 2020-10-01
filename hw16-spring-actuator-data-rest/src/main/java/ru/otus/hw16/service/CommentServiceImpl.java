package ru.otus.hw16.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw16.model.Comment;
import ru.otus.hw16.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  @Override
  @Transactional
  public Comment save(Comment entity) {
    return commentRepository.save(entity);
  }

  @Override
  public List<Comment> readAllForBook(String bookId) {
    return commentRepository.findAllByBookId(bookId);
  }
}
