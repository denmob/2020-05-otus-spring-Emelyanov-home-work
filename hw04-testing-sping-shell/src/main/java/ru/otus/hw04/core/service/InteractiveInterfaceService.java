package ru.otus.hw04.core.service;

import ru.otus.hw04.core.domain.Question;
import ru.otus.hw04.core.domain.Answer;
import ru.otus.hw04.core.domain.Student;

public interface InteractiveInterfaceService {

  String getNameStudent();

  Answer processTest(Question question);

  void printResult(Student student);
}
