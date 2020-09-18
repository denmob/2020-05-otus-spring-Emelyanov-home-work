package ru.otus.hw14.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
@ToString(exclude = "commentEntities")
@NamedEntityGraph(name = "book-genre-entity-graph", attributeNodes = {@NamedAttributeNode("genreEntity")})
public class BookEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title", nullable = false, unique = true)
  private String title;

  @Column(name = "date", nullable = false)
  private Date date;

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private AuthorEntity authorEntity;

  @ManyToOne
  @JoinColumn(name = "genre_id", referencedColumnName = "id")
  private GenreEntity genreEntity;

  @OneToMany(mappedBy = "bookEntity", fetch = FetchType.LAZY)
  private List<CommentEntity> commentEntities = new ArrayList<>();
}
