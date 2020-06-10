package ru.otus.hw02.api;

public class MyHw02Exception extends RuntimeException {
  private static final long serialVersionUID = -8460356990632230194L;

  public MyHw02Exception(String message, Throwable cause) {
    super(message, cause);
  }

  public MyHw02Exception(String message) {
    super(message);
  }

  public MyHw02Exception(Throwable cause) {
    super(cause);
  }
}
