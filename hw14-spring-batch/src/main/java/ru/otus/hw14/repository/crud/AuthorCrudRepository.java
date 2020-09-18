package ru.otus.hw14.repository.crud;


import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14.model.entity.AuthorEntity;

import java.util.Date;
import java.util.Optional;

public interface AuthorCrudRepository extends CrudRepository<AuthorEntity, Long> {

  Optional<AuthorEntity> findByFirstNameAndLastNameAndBirthday(String firstName, String lastName, Date birthday);
}
