package ru.otus.hw02.core.test;

import ru.otus.hw02.api.test.TestProcess;

import java.util.HashMap;
import java.util.Map;

public class TestProcessImpl implements TestProcess {

  private final Map<String, Integer> answers = new HashMap<>();

  @Override
  public void putAnswer(String answerName, int variant) {
    if (answerName == null || answerName.isEmpty()) throw new IllegalArgumentException("Param answerName is null or empty");
    if (variant < 0) throw new IllegalArgumentException("Param variant contains negative value");
    answers.put(answerName, variant);
  }

  @Override
  public Map<String, Integer> getTestResult() {
    return answers;
  }
}
