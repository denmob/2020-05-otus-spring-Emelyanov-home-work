package ru.otus.hw02.api.gui;

import ru.otus.hw02.api.MyHw02Exception;

public class InteractiveInterfaceException extends MyHw02Exception {
  public InteractiveInterfaceException(Exception ex) {
    super(ex);
  }

  public InteractiveInterfaceException(String cause) {
    super(cause);
  }
}
