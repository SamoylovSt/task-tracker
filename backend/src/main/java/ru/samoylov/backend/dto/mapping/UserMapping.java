package ru.samoylov.backend.dto.mapping;

import org.mapstruct.Mapper;
import ru.samoylov.backend.dto.UserDto;
import ru.samoylov.backend.entity.User;

@Mapper
public interface UserMapping {
    UserDto toDto(User user);
}
