package ru.otus.hw02.core.test;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.hw02.api.reader.DataReader;
import ru.otus.hw02.api.test.TestValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TestValidatorImpl implements TestValidator {

  private final Map<String, Integer> validAnswers;

  public TestValidatorImpl(@Qualifier("csvReaderAnswer") DataReader csvReaderAnswer) {
    validAnswers = convertMap(csvReaderAnswer.getData());
  }

  @Override
  public int getGradeForTest(Map<String, Integer> answers) {
    if (answers == null) throw new IllegalArgumentException("Argument answers is null");

    int grade = 0;
    for (Map.Entry<String, Integer> entry : answers.entrySet()) {
      if (checkAnswer(entry.getKey(), entry.getValue())) {
        grade++;
      }
    }
    return grade;
  }


  private boolean checkAnswer(String questionName, Integer variant) {
    Integer value = validAnswers.get(questionName);
    if (value != null) {
      return value.equals(variant);
    }
    return false;
  }

  private Map<String, Integer> convertMap(Map<String, List<String>> oldMap) {
    Map<String, Integer> newMap = new HashMap<>();
    for (Map.Entry<String, List<String>> entry : oldMap.entrySet()) {
      newMap.put(entry.getKey(), Integer.valueOf(entry.getValue().get(0)));
    }
    return newMap;
  }

}
