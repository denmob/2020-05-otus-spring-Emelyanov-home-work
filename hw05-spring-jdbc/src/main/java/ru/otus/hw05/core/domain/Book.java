package ru.otus.hw05.core.domain;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

  private long id;
  private long authorId;
  private long genreId;
  private @NonNull String title;
  private @NonNull Date date;

}
