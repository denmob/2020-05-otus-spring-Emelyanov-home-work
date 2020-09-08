package ru.otus.hw13.controller;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String msg) {
    super(msg);
  }

  public NotFoundException(String msg, Throwable t) {
    super(msg, t);
  }
}
