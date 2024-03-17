package by.slizh.plants.web.mapper;

import by.slizh.plants.domain.user.User;
import by.slizh.plants.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);
}

