package ru.otus.hw02.core.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw02.api.reader.DataReader;
import ru.otus.hw02.api.reader.DataReaderException;


import java.io.*;
import java.util.*;

public class CsvReader implements DataReader {

  private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);

  private Map<String, List<String>> dataQuestions = new HashMap<>();

  private String cvsSplitBy;
  private InputStream csvInputStream;
  private String csvFile;

  public CsvReader(String csvFile, String cvsSplitBy) {
    if (csvFile == null || csvFile.isEmpty()) throw new IllegalArgumentException("CsvFile is null or empty!");
    if (cvsSplitBy == null || cvsSplitBy.isEmpty()) throw new IllegalArgumentException("cvsSplitBy is null or empty!");

    this.cvsSplitBy = cvsSplitBy;
    this.csvFile = csvFile;
    this.csvInputStream = createInputStreamFromResourceFile(this.csvFile);
  }

  @Override
  public Map<String, List<String>> getData() {
    splitLineToDataObject(readInputStreamToList(this.csvInputStream));
    return dataQuestions;
  }

  @Override
  public void setFile(String csvFile) {
    if (csvFile == null || csvFile.isEmpty()) throw new IllegalArgumentException("CsvFile is null or empty!");
    this.csvFile = csvFile;
    this.csvInputStream = createInputStreamFromResourceFile(this.csvFile);
  }

  @Override
  public void close() {
    try {
      if (csvInputStream != null) {
        csvInputStream.close();
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      throw new DataReaderException(e);
    }
  }

  private List<String> readInputStreamToList(InputStream csvInputStream) {
    if (csvInputStream == null) throw new IllegalStateException("csvInputStream is null!");
    String line;
    List<String> stringList = new ArrayList<>();
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(csvInputStream));
      while ((line = br.readLine()) != null) {
        stringList.add(line);
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      throw new DataReaderException(e);
    }
    return stringList;
  }

  private InputStream createInputStreamFromResourceFile(String sNameFile) {
    InputStream input = this.getClass().getResourceAsStream("/resource/" + sNameFile);
    if (input == null) {
      input = this.getClass().getClassLoader().getResourceAsStream(sNameFile);
    }
    return input;
  }

  private void splitLineToDataObject(List<String> stringList) {
    for (String line : stringList) {
      String[] questionNameWithVariants = line.split(cvsSplitBy);
      if (questionNameWithVariants.length > 1) {
        List<String> answers = new ArrayList<>(questionNameWithVariants.length);
        answers.addAll(Arrays.asList(questionNameWithVariants).subList(1, questionNameWithVariants.length));
        dataQuestions.put(questionNameWithVariants[0].trim(), answers);
      }
    }
  }

}
