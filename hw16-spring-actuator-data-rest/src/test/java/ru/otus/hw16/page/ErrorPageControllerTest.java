package ru.otus.hw16.page;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.StringContains.containsString;

@Slf4j
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@SpringBootTest(classes = {ErrorPageController.class})
class ErrorPageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @SneakyThrows
  void errorPage() {
    String code = "277";
    String uri = "/test";
    String message = "privet";

    String status_code = String.format("<h2>Status code: %s</h2>", code);
    String request_uri = String.format("<h2>Request uri: %s</h2>", uri);
    String error_message = String.format("<h2>Message: %s</h2>", message);

    mockMvc.perform(MockMvcRequestBuilders.get("/error")
        .requestAttr("javax.servlet.error.status_code", code)
        .requestAttr("javax.servlet.error.request_uri", uri)
        .requestAttr("javax.servlet.error.message", message))
        .andExpect(MockMvcResultMatchers.content().string(containsString(status_code)))
        .andExpect(MockMvcResultMatchers.content().string(containsString(request_uri)))
        .andExpect(MockMvcResultMatchers.content().string(containsString(error_message)));
  }

  @Test
  @SneakyThrows
  void errorPageWithEmptyRequestAttr() {
    String status_code = "<h2>Status code: null</h2>";
    String request_uri = "<h2>Request uri: null</h2>";
    String message = "<h2>Message: null</h2>";

    mockMvc.perform(MockMvcRequestBuilders.get("/error"))
        .andExpect(MockMvcResultMatchers.content().string(containsString(status_code)))
        .andExpect(MockMvcResultMatchers.content().string(containsString(request_uri)))
        .andExpect(MockMvcResultMatchers.content().string(containsString(message)));
  }

  @SpringBootConfiguration
  public static class StopWebMvcScan {
  }
}
