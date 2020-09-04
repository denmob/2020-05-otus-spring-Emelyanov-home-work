package ru.otus.hw13.test.config.security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.otus.hw13.model.User;
import ru.otus.hw13.security.model.SecurityUserDetails;


import java.util.Arrays;

@TestConfiguration
public class SpringSecurityAuxConfig {

  @Bean
  @Primary
  public UserDetailsService userDetailsServiceImpl() {
    User user = User.builder().username("user").password("123").role("ROLE_USER").build();
    User admin = User.builder().username("admin").password("456").role("ROLE_ADMIN").build();
    User test = User.builder().username("test").password("789").role("ROLE_ACL").build();
    return new InMemoryUserDetailsManager(Arrays.asList(new SecurityUserDetails(user), new SecurityUserDetails(admin), new SecurityUserDetails(test)));
  }
}
