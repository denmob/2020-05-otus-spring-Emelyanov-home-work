package ru.otus.hw15.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

import java.io.File;

@Configuration
public class FileWritingGatewayConfig {

  @Bean
  public MessageChannel writeToFileChannel() {
    DirectChannel directChannel = new DirectChannel();
    directChannel.subscribe(fileWritingMessageHandler());
    return directChannel;
  }

  @Bean
  public MessageHandler fileWritingMessageHandler() {
    Expression directoryExpression = new SpelExpressionParser().parseExpression("headers.directory");
    FileWritingMessageHandler handler = new FileWritingMessageHandler(directoryExpression);
    handler.setFileExistsMode(FileExistsMode.REPLACE);
    handler.setExpectReply(false);
    return handler;
  }

  @MessagingGateway(defaultRequestChannel = "writeToFileChannel")
  public interface FileWriteGateway {

    void writeToFile(@Header(FileHeaders.FILENAME) String fileName,
                     @Header("directory") File directory,
                     String data);

  }
}
