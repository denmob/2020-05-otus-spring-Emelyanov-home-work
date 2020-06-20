package ru.otus.hw04.core.service;

import ru.otus.hw04.core.domain.Student;

public interface ReplyMessageService {

  String getHelloMessage(String userName);

  String getWelcomeMessage();

  String getTestingFinishMessage();

  String getBeforeAnswerMessage();

  String getResultMessage(Student student);

  String getErrorMessage();

  String getNotTestingFinishedMessage();

  String getNotTestingStartedMessage();

  String getCanNotStartTestingMessage();

  String getAlreadyInitStartTestingMessage();

}
