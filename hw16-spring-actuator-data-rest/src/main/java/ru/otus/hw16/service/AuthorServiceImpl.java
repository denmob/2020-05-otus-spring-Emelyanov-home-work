package ru.otus.hw16.service;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw16.config.MonitoredService;
import ru.otus.hw16.model.Author;
import ru.otus.hw16.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@MonitoredService
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;

  @Override
  @Transactional
  @Timed(extraTags = {"componentClass", "AuthorServiceImpl", "methodName", "save", "componentType", "service"})
  public Author save(Author entity) {
    return authorRepository.save(entity);
  }

  @Override
  @Timed(extraTags = {"componentClass", "AuthorServiceImpl", "methodName", "findById", "componentType", "service"})
  public Optional<Author> findById(String authorId) {
    return authorRepository.findById(authorId);
  }

  @Override
  @Timed(extraTags = {"componentClass", "AuthorServiceImpl", "methodName", "findAll", "componentType", "service"})
  public List<Author> findAll() {
    return authorRepository.findAll();
  }
}
