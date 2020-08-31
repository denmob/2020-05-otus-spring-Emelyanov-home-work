package ru.otus.hw13.security.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw13.model.User;
import ru.otus.hw13.security.model.SecurityUserDetails;
import ru.otus.hw13.security.service.UserDetailsServiceImpl;
import ru.otus.hw13.service.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {UserDetailsServiceImpl.class, UserServiceImpl.class})
class UserDetailsServiceImplTest {

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @MockBean
  private UserServiceImpl userService;

  @Test
  void loadUserByUsername() {
    User user = User.builder().username("admin").password("12345").role("RLO_ADMIN").build();

    when(userService.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

    assertEquals(new SecurityUserDetails(user), userDetailsService.loadUserByUsername(user.getUsername()));

    verify(userService, times(1)).findUserByUsername(user.getUsername());
  }
}
