package ru.otus.hw03.core.service;

import ru.otus.hw03.core.domain.Question;
import ru.otus.hw03.core.domain.Answer;

public interface InteractiveInterface {

  String getNameStudent();

  Integer processTest(Question question, Answer answer);

  Integer getMark();
}
