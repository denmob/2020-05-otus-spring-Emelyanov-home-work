package ru.otus.hw12.security.config;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsServiceImpl;

  @Override
  public void configure( WebSecurity web ) {
    web.ignoring().antMatchers( "/" );
  }

  @Override
  @SneakyThrows
  protected void configure(HttpSecurity http) {
    http.csrf().disable()
        .httpBasic()
        .and()
        .formLogin()
        .loginPage("/login")
        .failureForwardUrl("/error")
        .successForwardUrl("/listBook")
        .usernameParameter("username")
        .passwordParameter("password")
        .and()
        .authorizeRequests()
        .antMatchers("/login").anonymous()
        .antMatchers(HttpMethod.GET, "/listBook").authenticated()
        .antMatchers(HttpMethod.GET, "/createBook", "/editBook", "/comment/book").hasRole("user")
        .antMatchers(HttpMethod.POST, "/saveBook").hasRole("admin")
        .antMatchers(HttpMethod.DELETE, "/deleteBook").hasRole("admin");
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
}
