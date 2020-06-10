package ru.otus.hw02.core.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.hw02.api.gui.InteractiveInterface;
import ru.otus.hw02.api.gui.InteractiveInterfaceException;
import ru.otus.hw02.api.question.Question;
import ru.otus.hw02.api.service.QuestionsService;


import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InteractiveInterfaceConsole implements InteractiveInterface {
  private static final Logger logger = LoggerFactory.getLogger(InteractiveInterfaceConsole.class);

  private final BufferedReader standardInput;
  private final PrintStream standardOutput;
  private final QuestionsService questionsService;

  private final Map<String, Integer> results = new HashMap<>();
  private String name = null;


  public InteractiveInterfaceConsole(QuestionsService questionsService, InputStream interfaceInputStream, OutputStream interfaceOutputStream) {
    if (questionsService == null) throw new IllegalArgumentException("Argument questionsService is null");
    if (interfaceInputStream == null) throw new IllegalArgumentException("Argument interfaceInputStream is null");
    if (interfaceOutputStream == null) throw new IllegalArgumentException("Argument interfaceOutputStream is null");

    this.questionsService = questionsService;
    this.standardInput = new BufferedReader(new InputStreamReader(interfaceInputStream));
    this.standardOutput = new PrintStream(interfaceOutputStream);
  }

  @Override
  public void startTest() {
    for (Question question : questionsService.getQuestions()) {
      Map.Entry<String, Integer> stringIntegerEntry = processingOneQuestion(question.getQuestion(), question.getVariantsToAnswer());
      results.put(stringIntegerEntry.getKey(), stringIntegerEntry.getValue());
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

  @Override
  public void welcome() {
    standardOutput.print("Enter your name: ");
    try {
      name = standardInput.readLine();
      if (name == null || name.isEmpty()) {
        throw new InteractiveInterfaceException("Name is null or empty!");
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      standardOutput.print("Please try again enter your name");
    }
    standardOutput.println("Hello " + name);
  }

  @Override
  public void printResultToTest(int grade) {
    standardOutput.println(String.format("%s test finish with grade:%s", getName(), grade));
  }

  @Override
  public Map.Entry<String, Integer> processingOneQuestion(String questions, List<String> answers) {
    if (questions == null || questions.isEmpty()) throw new IllegalArgumentException("Argument questions is null or empty");
    if (answers == null || answers.isEmpty()) throw new IllegalArgumentException("Argument answers is null or empty");

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
        if (!between(result, rowNumber - 1)) {
          standardOutput.println("Please choose row number with valid answer!");
          result = 0;
        }
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        standardOutput.println("Please choose row number with valid answer!");
      }
    } while (result == 0);
    return Map.entry(questions, result);
  }

  private boolean between(int i, int maxValueInclusive) {
    return i >= 1 && i <= maxValueInclusive;
  }


}
