package ru.otus.hw01.core.test;

import ru.otus.hw01.api.test.TestValidator;

import java.util.HashMap;
import java.util.Map;

public class TestValidatorImpl implements TestValidator {

  private final Map<String, Integer> validAnswers = new HashMap<>();

  public TestValidatorImpl() {
    createValidMap();
  }

  private void createValidMap() {
    validAnswers.put("The word is spelled correctly",5);
    validAnswers.put("Find a synonym for the word <To worry>",4);
    validAnswers.put("Find a common word for a given group of words",2);
    validAnswers.put("Mark the form of the verb in front of which you can put <to>",3);
    validAnswers.put("869 reads correctly",5);
  }

  private boolean checkAnswer(String questionName, Integer variant) {
    Integer value = validAnswers.get(questionName);
    if (value != null) {
      return value.equals(variant);
    }
    return false;
  }

  @Override
  public int getGradeForTest(Map<String, Integer> answers) {
    if (answers == null) throw new IllegalArgumentException("Incoming parameters is null");

    int grade = 0;
    for (Map.Entry<String, Integer> entry : answers.entrySet()) {
      if (checkAnswer(entry.getKey(), entry.getValue())) {
        grade++;
      }
    }
    return grade;
  }
}
