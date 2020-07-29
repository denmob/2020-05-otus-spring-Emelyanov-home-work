package ru.otus.hw09.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw09.model.Author;
import ru.otus.hw09.repository.AuthorRepository;


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
  public Optional<Author> readLastNameEquals(String lastName) {
    return authorRepository.findByLastNameEquals(lastName);
  }

  @Override
  @Transactional
  public boolean delete(String id) {
    return authorRepository.deleteAuthorById(id) == 1L;
  }

  @Override
  public List<Author> getAll() {
    return authorRepository.findAll();
  }

  @Override
  @Transactional
  public Author update(Author entity) {
    return authorRepository.save(entity);
  }
}
