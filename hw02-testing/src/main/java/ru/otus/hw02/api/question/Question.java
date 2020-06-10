package ru.otus.hw02.api.question;

import java.util.List;

public interface Question {

  String getQuestion();

  List<String> getVariantsToAnswer();

}
