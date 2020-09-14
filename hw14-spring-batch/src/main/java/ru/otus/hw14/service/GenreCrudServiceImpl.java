package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.table.Genre;
import ru.otus.hw14.repository.crud.GenreCrudRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreCrudServiceImpl implements GenreCrudService {

  private final GenreCrudRepository genreCrudRepository;

  @Override
  @Transactional
  public Genre save(Genre entity) {
    return genreCrudRepository.save(entity);
  }

  @Override
  @Transactional
  public void delete(Genre entity) {
    genreCrudRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    genreCrudRepository.deleteAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Genre> findById(String id) {
    return genreCrudRepository.findById(Long.valueOf(id));
  }

  @Override
  public Optional<Genre> findByName(String name) {
    return genreCrudRepository.findByName(name);
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<Genre> findAll() {
    return genreCrudRepository.findAll();
  }
}
