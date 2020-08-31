package ru.otus.hw13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw13.model.Author;
import ru.otus.hw13.repository.AuthorRepository;


import java.util.List;

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
  public List<Author> findAll() {
    return authorRepository.findAll();
  }
}
