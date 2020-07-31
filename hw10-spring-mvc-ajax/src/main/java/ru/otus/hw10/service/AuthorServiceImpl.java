package ru.otus.hw10.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw10.model.Author;
import ru.otus.hw10.repository.AuthorRepository;


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
  public Optional<Author> findByLastNameEquals(String authorLastName) {
    return  authorRepository.findByLastNameEquals(authorLastName);
  }

  @Override
  public boolean deleteAuthorByLastNameEquals(String authorLastName) {
    return authorRepository.deleteAuthorByLastNameEquals(authorLastName)== 1L;
  }

  @Override
  public boolean deleteAuthorById(String authorId) {
    return authorRepository.deleteAuthorById(authorId) == 1L;
  }

  @Override
  public List<Author> findAll() {
    return authorRepository.findAll();
  }
}
