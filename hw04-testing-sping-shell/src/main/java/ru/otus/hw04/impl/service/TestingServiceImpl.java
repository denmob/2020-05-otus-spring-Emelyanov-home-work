package ru.otus.hw04.impl.service;

import org.springframework.stereotype.Service;
import ru.otus.hw04.core.domain.Answer;
import ru.otus.hw04.core.domain.Question;
import ru.otus.hw04.core.domain.Student;
import ru.otus.hw04.core.service.InteractiveInterfaceService;
import ru.otus.hw04.core.service.QuestionsService;
import ru.otus.hw04.core.service.TestValidatorService;
import ru.otus.hw04.core.service.TestingService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestingServiceImpl implements TestingService {

  private final QuestionsService questionsService;
  private final InteractiveInterfaceService interactiveInterfaceService;
  private final TestValidatorService testValidatorService;

  public TestingServiceImpl(QuestionsService questionsService, InteractiveInterfaceService interactiveInterfaceService, TestValidatorService testValidatorService) {
    this.questionsService = questionsService;
    this.interactiveInterfaceService = interactiveInterfaceService;
    this.testValidatorService = testValidatorService;
  }

  @Override
  public void startTesting() {
    Student student = new Student(interactiveInterfaceService.getNameStudent());
    List<Answer> answers = new ArrayList<>();
    for (Question question : questionsService.getQuestions()) {
      answers.add(interactiveInterfaceService.processTest(question));
    }
    student.setAnswers(answers);
    student.setMark(testValidatorService.getMarkForQuestion(answers));

    interactiveInterfaceService.printResult(student);
  }
}
