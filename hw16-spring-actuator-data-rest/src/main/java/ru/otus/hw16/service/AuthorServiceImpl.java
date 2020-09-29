package ru.otus.hw16.service;

import com.codahale.metrics.annotation.Gauge;
import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw16.model.Author;
import ru.otus.hw16.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Metrics(registry = AuthorServiceImpl.REGISTRY_NAME)
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;
  public static final String REGISTRY_NAME = "authorServiceImpl";

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
  @Timed(name = "singleTimedMethod")
  @Gauge(name = "singleGaugeMethod")
  public List<Author> findAll() {
    return authorRepository.findAll();
  }
}
