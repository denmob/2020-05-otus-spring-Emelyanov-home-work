package ru.otus.hw02.api.test;

import ru.otus.hw02.api.MyHw02Exception;

public class TestValidatorException extends MyHw02Exception {
  public TestValidatorException(Exception ex) {
    super(ex);
  }
}
