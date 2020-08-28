package ru.otus.hw12.security.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsServiceImpl;
  private final AccessDeniedHandler accessDeniedHandler;

  @Override
  @SneakyThrows
  protected void configure(HttpSecurity http) {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "/login", "/error").permitAll()
        .antMatchers("/comment/list").hasRole("USER")
        .antMatchers("/book/create", "/book/edit","/book/save","/book/delete").hasRole("ADMIN")
        .antMatchers("/book/list").authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .defaultSuccessUrl("/book/list")
        .usernameParameter("username")
        .passwordParameter("password")
        .and()
        .logout()
        .permitAll()
        .and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
  }

  @Autowired
  @SneakyThrows
  public void configureAuthenticationProvider(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authProvider());
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsServiceImpl);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) {
    web
        .ignoring()
        .antMatchers("/static/**");
  }
}
