package ru.otus.hw12.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw12.model.User;
import ru.otus.hw12.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = UserServiceImpl.class)
class UserServiceImplTest {

  @Autowired
  private UserServiceImpl userService;

  @MockBean
  private UserRepository userRepository;

  @Test
  void findUserByUsername() {
    User user = User.builder().username("admin").password("12345").role("RLO_ADMIN").build();

    when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

    assertEquals(Optional.of(user), userService.findUserByUsername(user.getUsername()));

    verify(userRepository, times(1)).findUserByUsername(user.getUsername());
  }
}
