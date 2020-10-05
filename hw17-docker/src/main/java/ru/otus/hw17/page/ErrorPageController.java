package ru.otus.hw17.page;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorPageController implements ErrorController {

  private static final String ERROR_PATH = "/error";
  private static final String ERROR_TEMPLATE = "error/error";

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  @GetMapping(value = ERROR_PATH, produces = "text/html")
  public ModelAndView error(HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView(ERROR_TEMPLATE);

    modelAndView.addObject("code","Status code: "+ request.getAttribute("javax.servlet.error.status_code"));
    modelAndView.addObject("uri", "Request uri: "+request.getAttribute("javax.servlet.error.request_uri"));
    modelAndView.addObject("message","Message: "+ request.getAttribute("javax.servlet.error.message"));

    return modelAndView;
  }
}
