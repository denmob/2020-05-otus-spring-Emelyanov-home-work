package ru.otus.hw16.config;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.otus.hw16.service.AuthorServiceImpl;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

@Component
@Configuration
public class MetricRegistryConfig {

  private static final MetricRegistry REGISTRY = new MetricRegistry();

  public MetricRegistryConfig() {
    createAndStartReport();
  }

  private void createAndStartReport() {
    SharedMetricRegistries.add(AuthorServiceImpl.REGISTRY_NAME, REGISTRY);

    ConsoleReporter.forRegistry(REGISTRY)
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS)
        .outputTo(new PrintStream(System.out))
        .build()
        .start(3, TimeUnit.SECONDS);
  }
}
