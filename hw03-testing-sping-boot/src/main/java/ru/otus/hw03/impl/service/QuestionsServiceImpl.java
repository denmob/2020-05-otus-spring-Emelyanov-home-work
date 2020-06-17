package ru.otus.hw03.impl.service;

import org.springframework.stereotype.Service;
import ru.otus.hw03.core.domain.Question;
import ru.otus.hw03.core.service.FileReaderService;
import ru.otus.hw03.core.service.QuestionsService;
import ru.otus.hw03.impl.configs.YamlProps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionsServiceImpl implements QuestionsService {

  private final YamlProps yamlProps;
  private final FileReaderService fileReaderService;
  private final List<Question> questions = new ArrayList<>();

  public QuestionsServiceImpl(YamlProps yamlProps, FileReaderService fileReaderService) {
    this.yamlProps = yamlProps;
    this.fileReaderService = fileReaderService;
    createQuestions();
  }

  @Override
  public List<Question> getQuestions() {
    return questions;
  }

  private void createQuestions() {
    List<String> data = fileReaderService.getData(yamlProps.getQuestionsFile());
    for (String line : data) {
      String[] strings = line.split(yamlProps.getCsvSplit());
      questions.add(new Question(strings[0].trim(), Arrays.asList(strings).subList(1, strings.length)));
    }
  }
}
