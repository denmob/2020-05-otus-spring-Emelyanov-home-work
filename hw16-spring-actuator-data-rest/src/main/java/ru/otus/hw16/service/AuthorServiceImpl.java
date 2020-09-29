package ru.otus.hw16.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw16.model.Author;
import ru.otus.hw16.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;

  @Override
  @Transactional
  public Author save(Author entity) {
    return authorRepository.save(entity);
  }

  @Override
  public Optional<Author> findById(String authorId) {
    return authorRepository.findById(authorId);
  }

  @Override
  public List<Author> findAll() {
    return authorRepository.findAll();
  }
}
