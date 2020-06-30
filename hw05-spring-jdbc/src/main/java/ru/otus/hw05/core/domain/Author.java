package ru.otus.hw05.core.domain;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

  private long id;
  private @NonNull String firstName;
  private @NonNull String lastName;
  private @NonNull Date birthday;

}
