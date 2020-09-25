package ru.otus.hw15.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.*;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.dsl.Files;

import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Component;
import ru.otus.hw15.domain.Income;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Configuration
public class FileReadFlowConfig {

  @Bean
  public PollableChannel fileReadingResultChannel() {
    return new QueueChannel(100);
  }

  @Slf4j
  public static class FileReaderFinishedMessageHandler implements MessageHandler {
    @Override
    @SneakyThrows
    public void handleMessage(final Message<?> message) {
      log.info("FileReaderFinishedMessageHandler: {} ", message.getPayload());
    }
  }

  public Consumer<SourcePollingChannelAdapterSpec> sourcePollingChannelAdapterSpecConsumer() {
    return e -> e.poller(Pollers.fixedDelay(2000))
        .autoStartup(true);
  }

  @Bean
  public IntegrationFlow fileReadingFlow(StringToIncomeTransformer stringToIncomeTransformer, FileInboundChannelAdapter fileInboundChannelAdapter) {
    return IntegrationFlows
        .from(fileInboundChannelAdapter.getFileReadingMessageSource(), sourcePollingChannelAdapterSpecConsumer())
        .transform(Files.toStringTransformer())
        .transform(stringToIncomeTransformer)
        .channel("fileReadingResultChannel")
        .handle(new FileReaderFinishedMessageHandler())
        .get();
  }

  @Component
  public static class StringToIncomeTransformer extends AbstractTransformer {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @SneakyThrows
    protected List<Income> doTransform(Message<?> message) {
      return Arrays.asList(mapper.readValue(message.getPayload().toString(), Income[].class));
    }
  }

  @Getter
  @Component
  public static class FileInboundChannelAdapter {

    private final FileReadingMessageSource fileReadingMessageSource;
    private final File directory;

    public FileInboundChannelAdapter(@Value("${app.read.filterMask}") String filterMask,
                                     @Value("${app.read.childDirectory}") String childDirectory) {
      directory = new File(".", childDirectory);
      fileReadingMessageSource = Files.inboundAdapter(directory)
          .scanEachPoll(Boolean.TRUE)
          .patternFilter(filterMask)
          .useWatchService(true)
          .watchEvents(FileReadingMessageSource.WatchEventType.CREATE,
              FileReadingMessageSource.WatchEventType.MODIFY,
              FileReadingMessageSource.WatchEventType.DELETE).get();
    }
  }
}
