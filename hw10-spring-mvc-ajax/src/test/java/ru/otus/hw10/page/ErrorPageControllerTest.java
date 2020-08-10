package ru.otus.hw10.page;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureWebMvc
@AutoConfigureMockMvc
@SpringBootTest(classes = {ErrorPageController.class})
class ErrorPageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @SneakyThrows
  void errorPage() {
    String status_code = "277";
    String request_uri = "/test";

    String expect = String.format("Error with status code: %s request uri: %s",status_code,request_uri);

    mockMvc.perform(MockMvcRequestBuilders.get("/error")
        .requestAttr("javax.servlet.error.status_code", "277")
        .requestAttr("javax.servlet.error.request_uri", "/test"))
        .andExpect(MockMvcResultMatchers.content().string(containsString(expect)));
  }

  @Test
  @SneakyThrows
  void errorPageWithEmptyRequestAttr() {
    String expect = "Error with status code: null request uri: null";

    mockMvc.perform(MockMvcRequestBuilders.get("/error"))
        .andExpect(MockMvcResultMatchers.content().string(containsString(expect)));;
  }
}
