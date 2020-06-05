package ru.otus.hw02.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.hw02.api.service.TestingService;
import ru.otus.hw02.api.gui.InteractiveInterface;
import ru.otus.hw02.api.test.TestProcess;
import ru.otus.hw02.api.test.TestValidator;
import ru.otus.hw02.core.test.TestProcessImpl;

import java.util.Map;

@Service
public class TestingServiceImpl implements TestingService {
  private static final Logger logger = LoggerFactory.getLogger(TestingServiceImpl.class);

  private final TestProcess testProcess = new TestProcessImpl();
  private final TestValidator testValidator;
  private final InteractiveInterface interactiveInterface;


  public TestingServiceImpl(TestValidator testValidator, InteractiveInterface interactiveInterface) {
    this.testValidator = testValidator;
    this.interactiveInterface = interactiveInterface;
  }

  @Override
  public void startTest() {
    interactiveInterface.startTest();
    for (Map.Entry<String, Integer> entry : interactiveInterface.getResult().entrySet()) {
      testProcess.putAnswer(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public int getGradeForTest() {
    return testValidator.getGradeForTest(testProcess.getTestResult());
  }
}
