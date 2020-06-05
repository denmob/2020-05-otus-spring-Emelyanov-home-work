package ru.otus.hw02.api.test;

import java.util.Map;

public interface TestProcess {
  void putAnswer(String answerName, int variant);

  Map<String, Integer> getTestResult();
}
