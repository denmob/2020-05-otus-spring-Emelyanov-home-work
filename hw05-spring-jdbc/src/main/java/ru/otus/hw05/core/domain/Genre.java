package ru.otus.hw05.core.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Genre {

  private final @NonNull Long id;
  private final @NonNull String name;

}
