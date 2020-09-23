package ru.otus.hw15.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

import java.util.Objects;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ImportResource("classpath:http-gateway.xml")
public class HttpGatewayConfig {

  private final MessageChannel httpRequestChannel;
  private final PollableChannel httpReplyChannel;

  @Bean
  @SneakyThrows
  public CommandLineRunner commandLineRunner() {
    return args -> {
      while (true) {
        Message<String> requestMessage = MessageBuilder.withPayload("request message").build();
        log.info("request message" + requestMessage.toString());
        httpRequestChannel.send(requestMessage);
        Message<?> replyMessage = httpReplyChannel.receive();
        log.info("reply message" + Objects.requireNonNull(replyMessage).toString());
        Thread.sleep(2000);
      }
    };
  }
}
