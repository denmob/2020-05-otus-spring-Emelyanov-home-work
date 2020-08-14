package ru.otus.hw03.core.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Student {

  private final @NonNull String name;
  private List<Answer> answers;
  private Integer mark;

}
