package ru.otus.hw03.core.service;

import ru.otus.hw03.core.domain.Answer;
import ru.otus.hw03.core.domain.Question;

public interface TestValidatorService {
  int getMarkForQuestion(Question question, Answer answer);
}
