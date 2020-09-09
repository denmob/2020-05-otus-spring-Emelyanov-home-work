package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.table.Comment;
import ru.otus.hw14.repository.crud.CommentCrudRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentCrudServiceImpl implements CommentCrudService {

  private final CommentCrudRepository commentCrudRepository;

  @Override
  @Transactional
  public Comment save(Comment entity) {
    return commentCrudRepository.save(entity);
  }

  @Override
  @Transactional
  public void delete(Comment entity) {
    commentCrudRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    commentCrudRepository.deleteAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Comment> findById(String id) {
    return commentCrudRepository.findById(Long.valueOf(id));
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<Comment> findAll() {
    return commentCrudRepository.findAll();
  }
}
