package ru.otus.hw06.impl.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw06.core.models.Comment;
import ru.otus.hw06.core.repositories.GenreRepositoryJpa;
import ru.otus.hw06.core.models.Genre;

import javax.persistence.*;
import java.util.*;

@Transactional
@Repository
@RequiredArgsConstructor
@SuppressWarnings({"ConstantConditions", "SqlDialectInspection"})
public class GenreRepositoryJpaImpl implements GenreRepositoryJpa {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public long count() {
    String sql = "select count(g) from Genre g";
    return entityManager.createQuery(sql, Long.class).getSingleResult();
  }

  @Override
  @Transactional
  public Genre insert(Genre genre) {
    if (genre.getId() <= 0) {
      entityManager.persist(genre);
      return genre;
    } else {
      return entityManager.merge(genre);
    }
  }

  @Override
  public Optional<Genre> getById(long id) {
    return Optional.ofNullable(entityManager.find(Genre.class, id));
  }

  @Override
  public List<Genre> getAll() {
    String sql = "select g from Genre g";
    TypedQuery<Genre> query = entityManager.createQuery(sql, Genre.class);
    return query.getResultList();
  }

  @Override
  @Transactional
  public boolean deleteById(long id) {
    Optional<Genre> optionalGenre = getById(id);
    if (optionalGenre.isPresent()) {
      entityManager.remove(optionalGenre.get());
      return true;
    }
    return false;
  }

}
