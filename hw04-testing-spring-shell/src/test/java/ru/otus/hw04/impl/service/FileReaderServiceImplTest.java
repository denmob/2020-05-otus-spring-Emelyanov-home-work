package ru.otus.hw04.impl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw04.impl.configs.YamlProps;

import static org.junit.jupiter.api.Assertions.*;

@EnableConfigurationProperties(YamlProps.class)
@SpringBootTest(classes = {FileReaderServiceImpl.class,YamlProps.class})
class FileReaderServiceImplTest {

  @Autowired
  private FileReaderServiceImpl fileReaderService;

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

  @Test
  void getDataThrow() {
    assertThrows(NullPointerException.class, () -> fileReaderService.getData(null));
  }
}
