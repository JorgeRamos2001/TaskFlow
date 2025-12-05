package com.app.services;

import com.app.models.dtos.ProjectDTO;
import com.app.models.requests.CreateProjectRequest;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ProjectDTO createProject(CreateProjectRequest request);
    ProjectDTO getProjectById(UUID projectId);
    List<ProjectDTO> getAllProjects();
    List<ProjectDTO> getAllProjectsByName(String name);
    ProjectDTO updateProject(UUID projectId, CreateProjectRequest request);
    void deleteProject(UUID projectId);
}
