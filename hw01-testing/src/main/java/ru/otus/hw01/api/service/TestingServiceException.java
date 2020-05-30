package ru.otus.hw01.api.service;

public class TestingServiceException extends RuntimeException {
  public TestingServiceException(Exception ex) {
    super(ex);
  }
}
