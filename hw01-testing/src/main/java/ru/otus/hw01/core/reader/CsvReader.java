package ru.otus.hw01.core.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw01.api.reader.DataReader;
import ru.otus.hw01.api.reader.DataReaderException;


import java.io.*;
import java.util.*;

public class CsvReader implements DataReader {

  private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);

  private Map<String, List<String>> dataQuestions = new HashMap<>();

  private static final int MIN_VARIANT_FOR_ANSWER = 3;
  private static final String CVS_SPLIT_BY = ",";

  public CsvReader(String csvFile) {
    if (csvFile== null || csvFile.isEmpty()) throw new IllegalArgumentException("CsvFile is null or empty!");
    splitLineToDataObject(readFile(readResourceFile(csvFile)));
  }

  private List<String> readFile(InputStream csvFile) {
    if (csvFile== null) throw new IllegalArgumentException("Name csvFile incorrect!");
    String line = "";
    List<String> stringList = new ArrayList<>();

    try {
      try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile))) {
        while ((line = br.readLine()) != null) {
          stringList.add(line);
        }
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      throw new DataReaderException(e);
    }
    return stringList;
  }

  private InputStream readResourceFile(String sNameFile) {
    InputStream input = this.getClass().getResourceAsStream("/resource/" + sNameFile);
    if (input == null) {
      input = this.getClass().getClassLoader().getResourceAsStream(sNameFile);
    }
    return input;
  }

  private void splitLineToDataObject(List<String> stringList){
    for (String line: stringList) {
      String[] questionNameWithVariants = line.split(CVS_SPLIT_BY);
      if (questionNameWithVariants.length> MIN_VARIANT_FOR_ANSWER) {
        List<String> answers = new ArrayList<>(questionNameWithVariants.length);
        answers.addAll(Arrays.asList(questionNameWithVariants).subList(1, questionNameWithVariants.length));
        dataQuestions.put(questionNameWithVariants[0].trim(),answers);
      }
    }
  }


  @Override
  public Map<String, List<String>> getData() {
    return dataQuestions;
  }
}
