package ru.otus.hw13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw13.model.Genre;
import ru.otus.hw13.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

  private final GenreRepository genreRepository;

  @Override
  @Transactional
  public Genre save(Genre entity) {
    return genreRepository.save(entity);
  }

  @Override
  public List<Genre> findAll() {
    return genreRepository.findAll();
  }
}
