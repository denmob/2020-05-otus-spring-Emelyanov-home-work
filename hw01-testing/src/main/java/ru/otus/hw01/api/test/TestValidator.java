package ru.otus.hw01.api.test;

import java.util.Map;

public interface TestValidator {
  int getGradeForTest(Map<String,Integer> answers);
}
