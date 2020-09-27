package ru.otus.hw15.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.hw15.domain.Income;
import ru.otus.hw15.rest.IncomeController;
import ru.otus.hw15.service.IncomeServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@EnableAutoConfiguration
@ContextConfiguration(locations = "classpath:http-gateway-test.xml")
@SpringBootTest(classes = {IncomeController.class, IncomeServiceImpl.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class HttpGatewayConfigTest {

  private final ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private MessageChannel httpRequestChannel;
  @Autowired
  private PollableChannel httpReplyChannel;
  @MockBean
  private IncomeServiceImpl incomeService;

  @Test
  @SneakyThrows
  void httpReplyChannelReceive1() {
    List<Income> expect = new ArrayList<>();
    expect.add(new Income("test", 123.));
    when(incomeService.getIncome()).thenReturn(expect);

    Message<?> message = httpReplyChannel.receive();
    assertNotNull(message != null ? message.getPayload() : null);

    List<Income> actual = Arrays.asList(mapper.readValue(message.getPayload().toString(), Income[].class));

    assertEquals(expect, actual);
  }

  @Test
  void httpRequestChannelSend2() {
    assertTrue(httpRequestChannel.send(MessageBuilder.withPayload("request message").build()));

    Message<?> message = httpReplyChannel.receive();
    assertEquals("[]", Objects.requireNonNull(message).getPayload());
    verify(incomeService, times(1)).getIncome();
  }
}
