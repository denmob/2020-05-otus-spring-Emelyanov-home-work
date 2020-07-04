package ru.otus.hw06.impl.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
  private EntityManager em;

  @Override
  @Transactional(readOnly = true)
  public long count() {
    String sql = "select count(g) from Genre g";
    return em.createQuery(sql, int.class).getSingleResult();
  }

  @Override
  @Transactional(readOnly = false)
  public Genre insert(Genre genre) {
    if (genre.getId() <= 0) {
      em.persist(genre);
      return genre;
    } else {
      return em.merge(genre);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Genre> getById(long id) {
    return Optional.ofNullable(em.find(Genre.class, id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Genre> getAll() {
    String sql = "select g from Genre g";
    TypedQuery<Genre> query = em.createQuery(sql, Genre.class);
    return query.getResultList();
  }

  @Override
  @Transactional(readOnly = false)
  public void deleteById(long id) {
    String sql = "delete from Genre g where g.id = :id";
    Query query = em.createQuery(sql);
    query.setParameter("id", id);
    query.executeUpdate();
  }

}
