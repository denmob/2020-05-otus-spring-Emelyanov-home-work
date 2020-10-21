package ru.otus.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "error";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    log.info("ErrorFilter.run() Inside Route Filter");
    return null;
  }
}
