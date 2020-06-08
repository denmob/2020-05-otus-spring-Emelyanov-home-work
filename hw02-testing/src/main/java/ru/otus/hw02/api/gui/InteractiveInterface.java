package ru.otus.hw02.api.gui;

import java.util.List;
import java.util.Map;

public interface InteractiveInterface {

  void welcome();

  Map.Entry<String, Integer> processingOneQuestion(String questions, List<String> answers);

  void startTest();

  Map<String, Integer> getResult();

  String getName();

  void printResultToTest(int grade);
}
