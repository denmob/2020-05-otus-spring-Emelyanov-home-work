package ru.otus.hw02.api.service;

import ru.otus.hw02.api.MyHw02Exception;

public class TestingServiceException extends MyHw02Exception {
  public TestingServiceException(Exception ex) {
    super(ex);
  }
}
