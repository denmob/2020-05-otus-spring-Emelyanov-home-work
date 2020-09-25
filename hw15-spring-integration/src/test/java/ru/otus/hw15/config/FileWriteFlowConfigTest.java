package ru.otus.hw15.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@EnableIntegration
@ActiveProfiles("test")
@SpringBootTest(classes = {FileWriteFlowConfig.class, HttpReplyChannelTestConfig.class})
class FileWriteFlowConfigTest {

  @Autowired
  @Qualifier("httpReplyChannel")
  private MessageChannel httpReplyChannel;

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
  void writeToFileChannel() {
    Message<String> requestMessage = MessageBuilder.withPayload("request message").build();
    httpReplyChannel.send(requestMessage);
    Thread.sleep(100);
    String actual = new String(Files.readAllBytes(pathToWriteFile));
    assertEquals(requestMessage.getPayload(), actual);
  }
}
