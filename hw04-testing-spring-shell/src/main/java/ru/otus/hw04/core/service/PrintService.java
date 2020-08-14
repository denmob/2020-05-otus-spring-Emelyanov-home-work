package ru.otus.hw04.core.service;

import ru.otus.hw04.core.domain.Question;
import ru.otus.hw04.core.domain.Student;

public interface PrintService {

  void printQuestion(Question question);

  void printResult(Student student);

  void printWelcomeMessage();

  void printHelloMessage(String studentName);

  void printBeforeAnswerMessage();

  void printTestingFinishMessage();

  void printErrorMessage();

}
