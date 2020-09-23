package ru.otus.hw15.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ImportResource("classpath:http-gateway.xml")
public class HttpGatewayConfig {
}
