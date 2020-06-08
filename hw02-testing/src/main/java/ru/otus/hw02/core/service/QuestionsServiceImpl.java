package ru.otus.hw02.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.hw02.api.question.Question;
import ru.otus.hw02.api.reader.DataReader;
import ru.otus.hw02.api.service.QuestionsService;
import ru.otus.hw02.core.question.QuestionImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionsServiceImpl implements QuestionsService {

  private final String questionsFile;
  private final DataReader dataReader;
  private final List<Question> questions = new ArrayList<>();

  public QuestionsServiceImpl(@Value("${test.questions}") String questionsFile, DataReader dataReader) {
    this.questionsFile = questionsFile;
    this.dataReader = dataReader;
    createQuestions();
  }

  @Override
  public List<Question> getQuestions() {
    return questions;
  }

  private void createQuestions() {
    Map<String, List<String>> dataQuestions = dataReader.getData(questionsFile);
    for (Map.Entry<String, List<String>> entry : dataQuestions.entrySet()) {
      questions.add(new QuestionImpl(entry.getKey(), entry.getValue()));
    }
  }
}
