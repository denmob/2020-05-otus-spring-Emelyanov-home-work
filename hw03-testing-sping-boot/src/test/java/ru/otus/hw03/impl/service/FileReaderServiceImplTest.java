package ru.otus.hw03.impl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw03.core.service.FileReaderService;
import ru.otus.hw03.impl.configs.YamlProps;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileReaderServiceImplTest {

  @Autowired
  private FileReaderService fileReaderService;

  @Autowired
  private YamlProps yamlProps;

  @Test
  void getDataNotNull() {
    assertNotNull(fileReaderService.getData(yamlProps.getAnswersFile()));
  }

  @Test
  void getDataNotEmpty() {
    assertEquals(5, fileReaderService.getData(yamlProps.getAnswersFile()).size());
  }

}
