package ru.otus.hw03.impl.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.hw03.core.service.FileReaderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileReaderServiceImpl implements FileReaderService {

  @SneakyThrows
  @Override
  public List<String> getData(@NonNull String csvFile) {
    return readCsvFile(getInputStreamFromResourceFile(csvFile));
  }

  private InputStream getInputStreamFromResourceFile(@NonNull String csvFile) {
    InputStream input = this.getClass().getResourceAsStream("/resource/" + csvFile);
    if (input == null) {
      input = this.getClass().getClassLoader().getResourceAsStream(csvFile);
    }
    return input;
  }

  private List<String> readCsvFile(InputStream csvInputStream) throws IOException {
    List<String> stringList = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(csvInputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        stringList.add(line);
      }
      return stringList;
    }
  }

}
