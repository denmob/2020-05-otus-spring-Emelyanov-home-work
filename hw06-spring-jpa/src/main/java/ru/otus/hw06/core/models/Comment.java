package ru.otus.hw06.core.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "book")
@Table(name = "comments")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "commentary", nullable = false, unique = true)
  private @NonNull String commentary;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id", referencedColumnName = "id")
  private Book book;
}
