package ru.otus.hw14.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "bookEntity")
@Table(name = "comments")
public class CommentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "commentary", nullable = false, unique = true)
  private @NonNull String commentary;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id", referencedColumnName = "id")
  private BookEntity bookEntity;
}
