package ru.otus.hw01.api.test;

public class TestValidatorException extends RuntimeException {
  public TestValidatorException(Exception ex) {
    super(ex);
  }
}
