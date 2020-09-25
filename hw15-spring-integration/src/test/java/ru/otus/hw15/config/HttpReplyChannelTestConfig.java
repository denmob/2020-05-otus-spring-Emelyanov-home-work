package ru.otus.hw15.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.MessageChannel;

@TestConfiguration
public class HttpReplyChannelTestConfig {

  @Bean
  public MessageChannel httpReplyChannel() {
    return new QueueChannel(10);
  }
}
