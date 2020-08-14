package ru.otus.hw04.core.service;

public interface TestingService {

  void createStudent(String studentName);

  void startTesting();

  boolean setAnswer(String answer);

  void printResult();

  boolean isFinishTesting();

  boolean isStartedTesting();

  boolean isCanStartTesting();

}
