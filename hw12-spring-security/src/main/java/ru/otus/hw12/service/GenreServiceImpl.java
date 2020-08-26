package ru.otus.hw12.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw12.model.Genre;
import ru.otus.hw12.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

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
