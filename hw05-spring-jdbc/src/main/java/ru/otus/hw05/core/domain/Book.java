package ru.otus.hw05.core.domain;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

  private @NonNull Long id;
  private @NonNull Long authorId;
  private @NonNull Long genreId;
  private @NonNull String title;
  private @NonNull Date date;

}
