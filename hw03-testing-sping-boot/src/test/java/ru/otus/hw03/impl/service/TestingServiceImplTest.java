package ru.otus.hw03.impl.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw03.core.domain.Answer;
import ru.otus.hw03.core.domain.Question;
import ru.otus.hw03.core.domain.Student;
import ru.otus.hw03.core.service.InteractiveInterfaceService;
import ru.otus.hw03.core.service.QuestionsService;
import ru.otus.hw03.core.service.TestValidatorService;

import java.util.Collections;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TestingServiceImplTest {

  @Autowired
  private QuestionsService questionsService;

  @MockBean
  private InteractiveInterfaceService interactiveInterfaceService;

  @Autowired
  private TestValidatorService testValidatorService;

  @Autowired
  private TestingServiceImpl testingService;

  @Test
  void startTesting() {
    String name = "Den";
    Question question = questionsService.getQuestions().get(0);
    Answer answer = new Answer(question.getTitleQuestion(), 5);

    Mockito.when(interactiveInterfaceService.getNameStudent()).thenReturn(name);
    Mockito.when(interactiveInterfaceService.processTest(question)).thenReturn(answer);

    testingService.startTesting();

    Student student = new Student(name);
    student.setAnswers(Collections.singletonList(answer));
    student.setMark(testValidatorService.getMarkForQuestion(Collections.singletonList(answer)));

    verify(interactiveInterfaceService, times(1)).printResult(student);
  }
}
