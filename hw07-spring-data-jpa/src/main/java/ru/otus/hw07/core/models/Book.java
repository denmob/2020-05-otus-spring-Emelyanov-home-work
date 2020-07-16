package ru.otus.hw07.core.models;

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
@ToString(exclude = "comments")
@NamedEntityGraph(name = "book-genre-entity-graph", attributeNodes = {@NamedAttributeNode("genre")})
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title", nullable = false, unique = true)
  private String title;

  @Column(name = "date", nullable = false)
  private Date date;

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private Author author;

  @ManyToOne
  @JoinColumn(name = "genre_id", referencedColumnName = "id")
  private Genre genre;

  @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();
}
