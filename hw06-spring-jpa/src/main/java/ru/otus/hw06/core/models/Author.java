package ru.otus.hw06.core.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "first_name", nullable = false, unique = true)
  private @NonNull String firstName;

  @Column(name = "last_name", nullable = false, unique = true)
  private @NonNull String lastName;

  @Column(name = "birthday", nullable = false, unique = true)
  private @NonNull Date birthday;

}
