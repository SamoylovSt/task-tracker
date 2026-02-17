package ru.samoylov.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import ru.samoylov.backend.dto.UserDto;
import ru.samoylov.backend.dto.mapping.UserMapping;
import ru.samoylov.backend.repository.UserRepository;
import ru.samoylov.backend.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserMapping userMapping;
    public final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto getUserById(String id) throws ChangeSetPersister.NotFoundException {
        return userMapping.toDto(userRepository.findByUserId(UUID.fromString(id))
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
        //TODO s
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public String addUser(UserDto user) {
        return "";
    }
}
