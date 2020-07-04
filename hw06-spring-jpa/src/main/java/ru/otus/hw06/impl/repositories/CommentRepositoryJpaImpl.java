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
  private EntityManager em;

  @Override
  @Transactional(readOnly = true)
  public long count() {
    String sql = "select count(a) from Comment a";
    return em.createQuery(sql, int.class).getSingleResult();
  }

  @Override
  @Transactional(readOnly = false)
  public Comment insert(Comment comment) {
    if (comment.getId() <= 0) {
      em.persist(comment);
      return comment;
    } else {
      return em.merge(comment);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Comment> getById(long id) {
    return Optional.ofNullable(em.find(Comment.class, id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Comment> getAll() {
    String sql = "select a from Comment a";
    TypedQuery<Comment> query = em.createQuery(sql, Comment.class);
    return query.getResultList();
  }

  @Override
  @Transactional(readOnly = false)
  public void deleteById(long id) {
    String sql = "delete from Comment a where a.id = :id";
    Query query = em.createQuery(sql);
    query.setParameter("id", id);
    query.executeUpdate();
  }

}
