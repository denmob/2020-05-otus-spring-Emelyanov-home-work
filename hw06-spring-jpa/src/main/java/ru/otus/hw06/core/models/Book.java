package ru.otus.hw06.core.models;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "book-genre-entity-graph",
    attributeNodes = {@NamedAttributeNode("genre")})
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title", nullable = false, unique = true)
  private @NonNull String title;

  @Column(name = "date", nullable = false, unique = true)
  private @NonNull Date date;

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private Author author;

  @ManyToOne
  @JoinColumn(name = "genre_id", referencedColumnName = "id")
  private Genre genre;

  @Fetch(FetchMode.SELECT)
  @BatchSize(size = 5)
  @ManyToMany(targetEntity = Comment.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "book_comments", joinColumns = @JoinColumn(name = "book_id"),
      inverseJoinColumns = @JoinColumn(name = "comment_id"))
  private List<Comment> comments;

}
