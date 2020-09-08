package ru.otus.hw13.service;

import ru.otus.hw13.model.User;

import java.util.Optional;

public interface UserService {

  Optional<User> findUserByUsername(String userName);
}
