package ru.otus.hw02.api.test;

import ru.otus.hw02.api.MyHw02Exception;

public class TestProcessException extends MyHw02Exception {
  public TestProcessException(Exception ex) {
    super(ex);
  }
}
