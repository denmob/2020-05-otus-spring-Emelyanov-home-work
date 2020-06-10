package ru.otus.hw02.api.service;

import ru.otus.hw02.api.MyHw02Exception;

public class QuestionsServiceException extends MyHw02Exception {
  public QuestionsServiceException(Exception ex) {
    super(ex);
  }
}
