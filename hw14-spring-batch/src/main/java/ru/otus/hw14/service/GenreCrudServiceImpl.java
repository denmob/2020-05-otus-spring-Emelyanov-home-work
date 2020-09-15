package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.entity.GenreEntity;
import ru.otus.hw14.repository.crud.GenreCrudRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreCrudServiceImpl implements GenreCrudService {

  private final GenreCrudRepository genreCrudRepository;

  @Override
  @Transactional
  public GenreEntity save(GenreEntity entity) {
    return genreCrudRepository.save(entity);
  }

  @Override
  @Transactional
  public void delete(GenreEntity entity) {
    genreCrudRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    genreCrudRepository.deleteAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<GenreEntity> findById(String id) {
    return genreCrudRepository.findById(Long.valueOf(id));
  }

  @Override
  public Optional<GenreEntity> findByName(String name) {
    return genreCrudRepository.findByName(name);
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<GenreEntity> findAll() {
    return genreCrudRepository.findAll();
  }
}
