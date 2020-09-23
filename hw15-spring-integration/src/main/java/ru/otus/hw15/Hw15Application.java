package ru.otus.hw15;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import ru.otus.hw15.config.FileWritingGatewayConfig;
import ru.otus.hw15.domain.Income;
import ru.otus.hw15.service.IncomeService;
import ru.otus.hw15.service.IncomeServiceImpl;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Slf4j
@EnableIntegration
@SpringBootApplication
@IntegrationComponentScan
public class Hw15Application {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Hw15Application.class, args);

    IncomeService incomeService = context.getBean(IncomeServiceImpl.class);
    MessageChannel httpRequestChannel = (MessageChannel) context.getBean("httpRequestChannel");
    PollableChannel httpReplyChannel = (PollableChannel) context.getBean("httpReplyChannel");
    PollableChannel fileReadingResultChannel = (PollableChannel) context.getBean("fileReadingResultChannel");
    FileWritingGatewayConfig.FileWriteGateway fileWritingGateway = context.getBean(FileWritingGatewayConfig.FileWriteGateway.class);

    incomeService.save(new Income("test",7777.));

    httpRequestChannel.send(MessageBuilder.withPayload("request message").build());
    fileWritingGateway.writeToFile("replyContent.txt", new File("."), Objects.requireNonNull(httpReplyChannel.receive()).getPayload().toString());

    Message<?> message = fileReadingResultChannel.receive();
    List<Income> incomeList = (List<Income>) Objects.requireNonNull(message).getPayload();
    log.info("fileReadingResultChannel.receive "+incomeList.toString());
  }

}

