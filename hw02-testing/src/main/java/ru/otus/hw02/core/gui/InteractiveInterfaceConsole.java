package ru.otus.hw02.core.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw02.api.gui.InteractiveInterface;
import ru.otus.hw02.api.reader.DataReader;


import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteractiveInterfaceConsole implements InteractiveInterface {
  private static final Logger logger = LoggerFactory.getLogger(InteractiveInterfaceConsole.class);
  private final BufferedReader standardInput;
  private final PrintStream standardOutput;

  private final Map<String, Integer> results = new HashMap<>();
  private String name = null;
  private final DataReader dataReader;

  public InteractiveInterfaceConsole(DataReader dataReader, InputStream interfaceInputStream, OutputStream interfaceOutputStream) {
    this.dataReader = dataReader;
    this.standardInput = new BufferedReader(new InputStreamReader(interfaceInputStream));
    this.standardOutput = new PrintStream(interfaceOutputStream);
  }

  @Override
  public void startTest() {
    welcome();
    for (Map.Entry<String, List<String>> entry : dataReader.getData().entrySet()) {
      processingOneQuestion(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public Map<String, Integer> getResult() {
    return results;
  }

  @Override
  public String getName() {
    return name;
  }

  private void welcome() {
    standardOutput.print("Enter your name: ");
    if (name == null) {
      do {
        try {
          name = standardInput.readLine();
        } catch (IOException e) {
          logger.error(e.getMessage(), e);
          standardOutput.print("Please try again enter your name");
        }
      } while (name == null);
    }
    standardOutput.println("Hello " + name);
  }

  private void processingOneQuestion(String questions, List<String> answers) {
    standardOutput.println(questions + ":");

    int rowNumber = 1;
    for (String answer : answers) {
      standardOutput.println(rowNumber + ". " + answer);
      rowNumber++;
    }
    standardOutput.println("Please choose row number with valid answer");
    int result = 0;
    do {
      try {
        result = Integer.parseInt(standardInput.readLine());
        if (!between(result, rowNumber)) {
          standardOutput.println("Please choose row number with valid answer!");
          result = 0;
        }
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        standardOutput.println("Please choose row number with valid answer!");
      }
    } while (result == 0);
    results.put(questions, result);
  }

  private boolean between(int i, int maxValueInclusive) {
    return i >= 1 && i <= maxValueInclusive;
  }

}
