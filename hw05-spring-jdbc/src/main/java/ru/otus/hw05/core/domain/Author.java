package ru.otus.hw05.core.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class Author {

  private final @NonNull Long id;
  private final @NonNull String firstName;
  private final @NonNull String lastName;
  private final @NonNull Date birthday;

}
