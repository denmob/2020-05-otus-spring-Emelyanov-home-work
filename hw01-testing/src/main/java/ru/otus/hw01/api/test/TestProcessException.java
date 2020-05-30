package ru.otus.hw01.api.test;

public class TestProcessException extends RuntimeException {
  public TestProcessException(Exception ex) {
    super(ex);
  }
}
