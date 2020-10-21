package ru.otus.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "post";
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
    log.info("PostFilter.run() Inside Route Filter");
    return null;
  }
}
