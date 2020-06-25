package ru.otus.hw04.core.service;

import ru.otus.hw04.core.domain.Answer;

import java.util.List;

public interface TestValidatorService {

  int getMarkForQuestion(List<Answer> answer);
}
