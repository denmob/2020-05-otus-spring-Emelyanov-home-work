package ru.otus.hw16.config;

import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class MicrometerConfig {

  @Bean
  JvmThreadMetrics threadMetrics() {
    return new JvmThreadMetrics();
  }
}
