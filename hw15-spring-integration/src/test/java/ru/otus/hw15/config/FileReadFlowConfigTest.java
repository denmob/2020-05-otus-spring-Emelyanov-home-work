package ru.otus.hw15.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw15.domain.Income;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@EnableIntegration
@ActiveProfiles("test")
@SpringBootTest(classes = {FileReadFlowConfig.class, DefaultPollerTestConfig.class})
class FileReadFlowConfigTest {

  @Autowired
  private PollableChannel fileReadingResultChannel;

  @Autowired
  private FileReadFlowConfig.FileInboundChannelAdapter fileInboundChannelAdapter;

  @Autowired
  private FileReadFlowConfig.StringToIncomeTransformer stringToIncomeTransformer;

  @Value("${app.write.childDirectory}")
  private String childDirectory;

  @Value("${app.write.filename}")
  private String filename;

  private Path pathToWriteFile;

  @BeforeEach
  @SneakyThrows
  void beforeEach() {
    pathToWriteFile = Paths.get(new File(".", childDirectory).getAbsoluteFile().toString() + '\\' + filename);
    if (Files.exists(pathToWriteFile)) {
      Files.delete(pathToWriteFile);
    }
  }

  @Test
  @SneakyThrows
  void fileReadingResultChannel() {
    assertEquals(pathToWriteFile.toString(), fileInboundChannelAdapter.getDirectory().getAbsolutePath() + '\\' + filename);
    assertTrue(fileInboundChannelAdapter.getFileReadingMessageSource().isRunning());

    String dataFile = "[{\"description\":\"Hw15Application\",\"amount\":567.0}]";
    Files.write(pathToWriteFile, dataFile.getBytes());

    Message<?> message = fileReadingResultChannel.receive();

    assertNotNull(message);
    assertEquals("[AccountTransaction(description=Hw15Application, amount=567.0)]", message.getPayload().toString());
  }

  @Test
  @SneakyThrows
  void stringToIncomeTransformer() {
    String dataFile = "[{\"description\":\"Hw15Application\",\"amount\":567.0}]";
    Message<String> fileMessage = MessageBuilder.withPayload(dataFile).build();

    List<Income> actual = stringToIncomeTransformer.doTransform(fileMessage);

    assertEquals("[AccountTransaction(description=Hw15Application, amount=567.0)]", actual.toString());
  }
}
