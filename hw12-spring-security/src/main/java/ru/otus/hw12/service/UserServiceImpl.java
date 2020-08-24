package ru.otus.hw12.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw12.model.User;
import ru.otus.hw12.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public Optional<User> findUserByUsername(String userName) {
    return userRepository.findUserByUsername(userName);
  }
}
