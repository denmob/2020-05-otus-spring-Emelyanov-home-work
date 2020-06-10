package ru.otus.hw02.api.reader;

import ru.otus.hw02.api.MyHw02Exception;

public class DataReaderException extends MyHw02Exception {
  public DataReaderException(Exception ex) {
    super(ex);
  }
}
