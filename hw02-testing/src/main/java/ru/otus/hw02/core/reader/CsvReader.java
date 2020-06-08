package ru.otus.hw02.core.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.hw02.api.reader.DataReader;
import ru.otus.hw02.api.reader.DataReaderException;


import java.io.*;
import java.util.*;

@Service
public class CsvReader implements DataReader {

  private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);

  private String cvsSplitBy;

  public CsvReader(@Value("${csv.split}") String cvsSplitBy) {
    if (cvsSplitBy == null || cvsSplitBy.isEmpty()) throw new IllegalArgumentException("cvsSplitBy is null or empty!");
    this.cvsSplitBy = cvsSplitBy;
  }

  @Override
  public Map<String, List<String>> getData(String csvFile) {
    if (csvFile == null || csvFile.isEmpty()) throw new IllegalArgumentException("CsvFile is null or empty!");

    return readCsvFile(getInputStreamFromResourceFile(csvFile));
  }

  private InputStream getInputStreamFromResourceFile(String csvFile) {
    InputStream input = this.getClass().getResourceAsStream("/resource/" + csvFile);
    if (input == null) {
      input = this.getClass().getClassLoader().getResourceAsStream(csvFile);
    }
    return input;
  }

  private Map<String, List<String>> readCsvFile(InputStream csvInputStream) {
    if (csvInputStream == null) throw new IllegalStateException("csvFile is null!");

    Map<String, List<String>> stringList = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(csvInputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] strings = line.split(cvsSplitBy);
        stringList.put(strings[0].trim(), Arrays.asList(strings).subList(1, strings.length));
      }
      return stringList;
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      throw new DataReaderException(e);
    }
  }

}
