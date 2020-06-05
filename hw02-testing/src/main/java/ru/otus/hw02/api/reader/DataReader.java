package ru.otus.hw02.api.reader;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

public interface DataReader extends Closeable {
  Map<String, List<String>> getData();

  void setFile(String csvFile);

  void close();
}
