package ru.otus.hw17.config;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw17.util.HostUtil;

@Configuration
public class MicroMeterRegistryConfig {

  private static final String HOST_TAG_KEY = "host";
  private static final String APP_TAG_KEY = "appName";
  private static final String IP_TAG_KEY = "ip";
  private static final String PROFILE_KEY = "profile";

  @Value("${spring.application.name}")
  private String appName;
  @Value("${spring.profiles.active:local}")
  private String profile;
  private final HostUtil hostUtil;

  @Autowired
  public MicroMeterRegistryConfig(HostUtil hostUtil) {
    this.hostUtil = hostUtil;
  }

  @Bean
  @SneakyThrows
  MeterRegistryCustomizer<PrometheusMeterRegistry> configureMetricsRegistry() {
    return registry -> {
      registry.config().commonTags(APP_TAG_KEY, appName);
      registry.config().commonTags(HOST_TAG_KEY, hostUtil.getHost());
      registry.config()
          .commonTags(IP_TAG_KEY, registry.config().namingConvention().tagKey(hostUtil.getHostIp()));
      registry.config().commonTags(PROFILE_KEY, profile);
    };
  }
}
