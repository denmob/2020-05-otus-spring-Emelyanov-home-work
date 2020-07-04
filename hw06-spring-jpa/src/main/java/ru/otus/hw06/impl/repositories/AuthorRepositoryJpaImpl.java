package ru.otus.hw06.impl.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw06.core.repositories.AuthorRepositoryJpa;
import ru.otus.hw06.core.models.Author;

import javax.persistence.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
@SuppressWarnings({"ConstantConditions", "SqlDialectInspection"})
public class AuthorRepositoryJpaImpl implements AuthorRepositoryJpa {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional(readOnly = true)
  public long count() {
    String sql = "select count(a) from Author a";
    return entityManager.createQuery(sql, int.class).getSingleResult();
  }

  @Override
  @Transactional(readOnly = false)
  public Author insert(Author author) {
    if (author.getId() <= 0) {
      entityManager.persist(author);
      return author;
    } else {
      return entityManager.merge(author);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Author> getById(long id) {
    return Optional.ofNullable(entityManager.find(Author.class, id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Author> getAll() {
    String sql = "select a from Author a";
    TypedQuery<Author> query = entityManager.createQuery(sql, Author.class);
    return query.getResultList();
  }

  @Override
  @Transactional(readOnly = false)
  public void deleteById(long id) {
    String sql = "delete from Author a where a.id = :id";
    Query query = entityManager.createQuery(sql);
    query.setParameter("id", id);
    query.executeUpdate();
  }

}
