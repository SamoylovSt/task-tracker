package ru.samoylov.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samoylov.backend.dto.UserDto;
import ru.samoylov.backend.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserId(UUID id);
}
