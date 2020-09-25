package ru.otus.hw15.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.File;

@Configuration
public class FileWriteFlowConfig {

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
    handler.setFileExistsMode(FileExistsMode.REPLACE_IF_MODIFIED);
    handler.setExpectReply(false);
    return handler;
  }

  @Bean
  public IntegrationFlow fileWritingFlow(@Value("${app.write.filename}") String filename, @Value("${app.write.childDirectory}") String childDirectory ) {
    return IntegrationFlows.from("httpReplyChannel")
        .enrichHeaders(h -> h.header(FileHeaders.FILENAME, filename)
            .header("directory", new File(".", childDirectory)))
        .channel("writeToFileChannel")
        .get();
  }

  @Bean(name = PollerMetadata.DEFAULT_POLLER)
  public PollerMetadata poller() {
    return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
  }
}
