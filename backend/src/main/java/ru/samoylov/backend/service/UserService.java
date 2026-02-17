package ru.samoylov.backend.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import ru.samoylov.backend.dto.UserDto;

import java.util.Optional;

public interface UserService {

    UserDto getUserById(String id)throws ChangeSetPersister.NotFoundException;

    UserDto getUserByEmail(String email);

    String addUser(UserDto user);


}
