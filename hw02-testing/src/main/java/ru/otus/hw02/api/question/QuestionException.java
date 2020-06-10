package ru.otus.hw02.api.question;

import ru.otus.hw02.api.MyHw02Exception;

public class QuestionException extends MyHw02Exception {
  public QuestionException(Exception ex) {
    super(ex);
  }
}
