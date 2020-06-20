package ru.otus.hw04.core.service;

public interface TestingService {

  String getWelcomeMessage();

  String getHelloMessage(String studentName);

  void startTesting();

  void setAnswer(String answer);

  String printResult();

  boolean isFinishTesting();

  boolean isStartedTesting();

  boolean isCanStartTesting();

}
