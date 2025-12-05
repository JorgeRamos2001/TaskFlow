package com.app.mappers;

import com.app.models.dtos.TaskDTO;
import com.app.models.entities.Task;
import com.app.models.requests.CreateTaskRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    @Mapping(source = "project.name", target = "projectName")
    @Mapping(source = "project.id", target = "projectId")
    TaskDTO toDto(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "priority", ignore = true)
    @Mapping(target = "state", constant = "ACTIVE")
    Task toEntity(CreateTaskRequest request);
}
