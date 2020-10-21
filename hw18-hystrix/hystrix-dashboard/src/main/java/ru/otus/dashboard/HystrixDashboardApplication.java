package ru.otus.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableTurbine
@EnableHystrixDashboard
@SpringBootApplication
public class HystrixDashboardApplication {

  public static void main(String[] args) {
    SpringApplication.run(HystrixDashboardApplication.class, args);
  }
}
