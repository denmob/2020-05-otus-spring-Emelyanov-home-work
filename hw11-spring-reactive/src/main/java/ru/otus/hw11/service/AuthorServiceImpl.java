package ru.otus.hw11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxExtensionsKt;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Author;
import ru.otus.hw11.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;

  @Override
  @Transactional
  public Mono<Author> save(Author entity) {
    return authorRepository.save(entity);
  }

  @Override
  public Mono<Author> findByLastNameEquals(String authorLastName) {
    return  authorRepository.findByLastNameEquals(authorLastName);
  }

  @Override
  public Mono<Author> findById(String authorId) {
    return authorRepository.findById(authorId);
  }

  @Override
  public Mono<Void> deleteAuthorByLastNameEquals(String authorLastName) {
    return authorRepository.deleteAuthorByLastNameEquals(authorLastName);
  }

  @Override
  public Mono<Void> deleteAuthorById(String authorId) {
    return authorRepository.deleteAuthorById(authorId);
  }

  @Override
  public Flux<Author> findAll() {
    return authorRepository.findAll();
  }
}
