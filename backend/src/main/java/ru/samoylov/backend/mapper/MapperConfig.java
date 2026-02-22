package ru.samoylov.backend.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.samoylov.backend.dto.TaskResponse;
import ru.samoylov.backend.entity.Task;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        mapper.typeMap(Task.class, TaskResponse.class).addMappings(expression -> {
            expression.map(source -> source.getUser().getId(), TaskResponse::setUserId);
        });
        return mapper;
    }
}