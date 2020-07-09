package ru.otus.hw06.impl.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw06.core.models.Comment;
import ru.otus.hw06.core.repositories.CommentRepositoryJpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@SuppressWarnings({"ConstantConditions", "SqlDialectInspection"})
public class CommentRepositoryJpaImpl implements CommentRepositoryJpa {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public long count() {
    String sql = "select count(a) from Comment a";
    return entityManager.createQuery(sql, Long.class).getSingleResult();
  }

  @Override
  @Transactional
  public Comment insert(Comment comment) {
    if (comment.getId() <= 0) {
      entityManager.persist(comment);
      return comment;
    } else {
      return entityManager.merge(comment);
    }
  }

  @Override
  public Optional<Comment> getById(long id) {
    return Optional.ofNullable(entityManager.find(Comment.class, id));
  }

  @Override
  public List<Comment> getAll() {
    String sql = "select a from Comment a";
    TypedQuery<Comment> query = entityManager.createQuery(sql, Comment.class);
    return query.getResultList();
  }

  @Override
  @Transactional
  public boolean deleteById(long id) {
    String sql = "delete from Comment a where a.id = :id";
    Query query = entityManager.createQuery(sql);
    query.setParameter("id", id);
    return query.executeUpdate()>0;
  }

}
