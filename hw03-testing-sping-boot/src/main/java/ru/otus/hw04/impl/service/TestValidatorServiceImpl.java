package ru.otus.hw04.impl.service;

import org.springframework.stereotype.Service;
import ru.otus.hw04.core.domain.Answer;
import ru.otus.hw04.core.service.FileReaderService;
import ru.otus.hw04.core.service.TestValidatorService;
import ru.otus.hw04.impl.configs.YamlProps;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestValidatorServiceImpl implements TestValidatorService {

  private final YamlProps yamlProps;
  private final FileReaderService fileReaderService;
  private final List<Answer> answers = new ArrayList<>();

  public TestValidatorServiceImpl(YamlProps yamlProps, FileReaderService fileReaderService) {
    this.yamlProps = yamlProps;
    this.fileReaderService = fileReaderService;
    createValidAnswers();
  }

  @Override
  public int getMarkForQuestion(List<Answer> answer) {
    int mark = 0;
    for (Answer answer1 : answers) {
      if (answer.contains(answer1)) {
        mark++;
      }
    }
    return mark;
  }

  private void createValidAnswers() {
    List<String> data = fileReaderService.getData(yamlProps.getAnswersFile());
    for (String line : data) {
      String[] strings = line.split(yamlProps.getCsvSplit());
      answers.add(new Answer(strings[0].trim(), Integer.parseInt(strings[1].trim())));
    }
  }
}
