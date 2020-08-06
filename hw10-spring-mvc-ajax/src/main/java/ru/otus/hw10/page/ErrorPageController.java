package ru.otus.hw10.page;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorPageController implements ErrorController {

  private static final String ERROR_PATH = "/error";

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  @GetMapping(value = ERROR_PATH, produces = "text/html")
  public ModelAndView errorPage(HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("pageGlobalError");

    String message = String.format("Error with status code: %s request uri: %s",
        request.getAttribute("javax.servlet.error.status_code"), request.getAttribute("javax.servlet.error.request_uri"));

    modelAndView.addObject("message", message);
    return modelAndView;
  }
}

