package ru.otus.hw04.impl.service;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.otus.hw04.core.service.FileReaderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class FileReaderServiceImpl implements FileReaderService {

  @SneakyThrows
  @Override
  public List<String> getData(@NonNull String file) {
    return readInputStream(getInputStream(file));
  }

  private InputStream getInputStream(@NonNull String file) {
    InputStream input = this.getClass().getResourceAsStream(file);
    if (input == null) {
      input = this.getClass().getClassLoader().getResourceAsStream(file);
    }
    return input;
  }

  private List<String> readInputStream(InputStream inputStream) throws IOException {
    List<String> stringList = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        stringList.add(line);
      }
      return stringList;
    }
  }
}
