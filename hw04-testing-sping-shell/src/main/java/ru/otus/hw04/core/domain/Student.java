package ru.otus.hw04.core.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Student {

  private final @NonNull String name;
  private List<Answer> answers = new ArrayList<>();
  private int mark;

}
