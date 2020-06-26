package ru.otus.hw05.core.domain;

import lombok.*;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class Book {

  private final @NonNull Long id;
  private final @NonNull Long authorId;
  private final @NonNull Long genreId;
  private final @NonNull String title;
  private final @NonNull Date date;

}
