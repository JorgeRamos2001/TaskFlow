package com.app.mappers;

import com.app.models.dtos.UserDTO;
import com.app.models.entities.User;
import com.app.models.requests.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "accountState", constant = "ACTIVE")
    User toEntity(RegisterRequest request);
}
