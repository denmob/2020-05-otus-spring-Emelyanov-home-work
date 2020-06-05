package ru.otus.hw02.core.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.hw02.api.gui.InteractiveInterface;
import ru.otus.hw02.api.reader.DataReader;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InteractiveInterfaceConsole implements InteractiveInterface {
  private static final Logger logger = LoggerFactory.getLogger(InteractiveInterfaceConsole.class);
  private final BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));

  private final Map<String, Integer> results = new HashMap<>();
  private String name = null;
  private final DataReader dataReader;

  public InteractiveInterfaceConsole(DataReader dataReader) {
    this.dataReader = dataReader;
  }


  private void welcome() {
    System.out.print("Enter your name: ");
    if (name == null) {
      do {
        try {
          name = standardInput.readLine();
        } catch (IOException e) {
          logger.error(e.getMessage(), e);
          System.out.print("Please try again enter your name");
        }
      } while (name == null);
    }
    System.out.println("Hello " + name);
  }

  private void processingOneQuestion(String questions, List<String> answers) {
    System.out.println(questions + ":");
    int result = 0;
    int rowNumber = 1;
    for (String answer : answers) {
      System.out.println(rowNumber + ". " + answer);
      rowNumber++;
    }
    System.out.println("Please choose row number with valid answer");
    if (result == 0) {
      do {
        try {
          result = Integer.parseInt(standardInput.readLine());
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
          System.out.println("Please choose row number with valid answer!");
        }
      } while (result == 0);
    }
    results.put(questions, result);
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
}
