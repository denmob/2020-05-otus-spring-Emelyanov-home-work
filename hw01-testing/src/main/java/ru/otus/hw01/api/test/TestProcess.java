package ru.otus.hw01.api.test;

import java.util.Map;

public interface TestProcess {
  void putAnswer(String answerName, int variant);

  Map<String, Integer> getTestResult();
}
