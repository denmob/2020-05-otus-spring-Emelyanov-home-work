package ru.otus.hw15.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Component;
import ru.otus.hw15.domain.Income;

import java.io.File;
import java.util.Arrays;

@Configuration
public class FileReadingFlowConfig {

  @Bean
  public PollableChannel fileReadingResultChannel() {
    return new QueueChannel(100);
  }

  @Bean
  public IntegrationFlow fileReadingFlow(StringToIncomeTransformer stringToIncomeTransformer) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    return IntegrationFlows
        .from(Files.inboundAdapter(new File("."))
                .patternFilter("*.txt"),
            e -> e.poller(Pollers.fixedDelay(1000)))
        .transform(Files.toStringTransformer())
        .transform(stringToIncomeTransformer)
        .channel("fileReadingResultChannel")
        .get();
  }

  @Component
  public static class StringToIncomeTransformer extends AbstractTransformer {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @SneakyThrows
    protected Object doTransform(Message<?> message) {
      return Arrays.asList(mapper.readValue(message.getPayload().toString(), Income[].class));
    }
  }
}
