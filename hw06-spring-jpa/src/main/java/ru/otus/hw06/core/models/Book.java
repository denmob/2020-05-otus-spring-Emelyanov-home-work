package ru.otus.hw06.core.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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

}
