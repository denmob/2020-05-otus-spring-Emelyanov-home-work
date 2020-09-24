package ru.otus.hw15.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:http-gateway.xml")
public class HttpGatewayConfig {
}
