package ru.otus.hw10.page;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.hw10.model.Book;
import ru.otus.hw10.service.AuthorServiceImpl;
import ru.otus.hw10.service.GenreServiceImpl;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookPageController.class)
class BookPageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuthorServiceImpl authorService;

  @MockBean
  private GenreServiceImpl genreService;

  private Book book;

  @BeforeEach
  void beforeEach() {
    book = Book.builder().id("123").title("title").build();
  }

  @Test
  @SneakyThrows
  void root() {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(status().is(302))
        .andReturn();
    assertEquals("/pageBookList", mvcResult.getResponse().getRedirectedUrl());
  }

  @Test
  @SneakyThrows
  void booksPage() {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/pageBookList"))
        .andExpect(status().is(200))
        .andExpect(MockMvcResultMatchers.content().string(containsString("Home page")))
        .andReturn();

    assertEquals("text/html;charset=UTF-8", mvcResult.getResponse().getContentType());
  }

  @Test
  @SneakyThrows
  void pageBookCreate() {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/pageBookCreate"))
        .andExpect(status().is(200))
        .andExpect(MockMvcResultMatchers.content().string(containsString("Create book page")))
        .andDo(print())
        .andReturn();

    assertNotNull(mvcResult.getModelAndView().getModel());
    assertTrue(mvcResult.getModelAndView().getModel().containsKey("authors"));
    assertTrue(mvcResult.getModelAndView().getModel().containsKey("genres"));
  }

  @Test
  @SneakyThrows
  void pageBookEdit() {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/pageBookEdit/{bookId}", book.getId()))
        .andExpect(status().is(200))
        .andExpect(MockMvcResultMatchers.content().string(containsString("Edit book page")))
        .andDo(print())
        .andReturn();
    String requestUry = "/pageBookEdit/" + book.getId();
    assertEquals(requestUry, mvcResult.getRequest().getRequestURI());
    assertEquals("pageBookEdit", mvcResult.getModelAndView().getViewName());
    assertNotNull(mvcResult.getModelAndView().getModel());
    assertTrue(mvcResult.getModelAndView().getModel().containsKey("authors"));
    assertTrue(mvcResult.getModelAndView().getModel().containsKey("genres"));
  }

  @SpringBootConfiguration
  @ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class))
  public static class StopWebMvcScan {
  }
}
