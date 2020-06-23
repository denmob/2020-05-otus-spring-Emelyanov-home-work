package ru.otus.hw03.core.service;

import ru.otus.hw03.core.domain.Question;
import ru.otus.hw03.core.domain.Answer;
import ru.otus.hw03.core.domain.Student;

public interface InteractiveInterfaceService {

  String getNameStudent();

  Answer processTest(Question question);

  void printResult(Student student);
}
