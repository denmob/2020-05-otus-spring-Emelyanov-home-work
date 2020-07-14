package ru.otus.hw07.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw07.core.models.Author;
import ru.otus.hw07.core.repositories.AuthorRepository;
import ru.otus.hw07.core.service.CRUDServiceAuthor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDAuthorService implements CRUDServiceAuthor {

  private final AuthorRepository authorRepository;

  @Override
  public Author create(Author entity) {
    return authorRepository.save(entity);
  }

  @Override
  public Optional<Author> read(long id) {
    return authorRepository.findById(id);
  }

  @Override
  public void delete(long id) {
    authorRepository.deleteById(id);
  }

  @Override
  public Author update(Author entity) {
    return authorRepository.save(entity);
  }
}
