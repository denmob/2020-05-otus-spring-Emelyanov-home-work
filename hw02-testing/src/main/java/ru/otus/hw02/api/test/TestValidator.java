package ru.otus.hw02.api.test;

import java.util.Map;

public interface TestValidator {
  int getGradeForTest(Map<String, Integer> answers);
}
