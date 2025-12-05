package com.app.services.implementations;

import com.app.exceptions.InvalidOperationException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.mappers.ProjectMapper;
import com.app.models.dtos.ProjectDTO;
import com.app.models.entities.Project;
import com.app.models.entities.User;
import com.app.models.requests.CreateProjectRequest;
import com.app.repositories.ProjectRepository;
import com.app.repositories.UserRepository;
import com.app.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional
    public ProjectDTO createProject(CreateProjectRequest request) {
        User user = getAuthenticatedUser();
        Project project = Project
                .builder()
                .name(request.name())
                .description(request.description())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
        return projectMapper.toDTO(projectRepository.save(project));
    }

    @Override
    public ProjectDTO getProjectById(UUID projectId) {
        User user = getAuthenticatedUser();
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found with the id: " + projectId));
        if (!project.getUser().equals(user)) {
            throw new InvalidOperationException("You are not allowed to access this project");
        }
        return projectMapper.toDTO(project);
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        User user = getAuthenticatedUser();
        return projectRepository.findAllByUser(user).stream().map(projectMapper::toDTO).toList();
    }

    @Override
    public List<ProjectDTO> getAllProjectsByName(String name) {
        User user = getAuthenticatedUser();
        return projectRepository.findAllByUserAndNameContainingIgnoreCase(user, name).stream().map(projectMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public ProjectDTO updateProject(UUID projectId, CreateProjectRequest request) {
        User user = getAuthenticatedUser();
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found with the id: " + projectId));
        if (!project.getUser().equals(user)) {
            throw new InvalidOperationException("You are not allowed to access this project");
        }
        project.setName(request.name());
        project.setDescription(request.description());
        return projectMapper.toDTO(projectRepository.save(project));
    }

    @Override
    @Transactional
    public void deleteProject(UUID projectId) {
        User user = getAuthenticatedUser();
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found with the id: " + projectId));
        if (!project.getUser().equals(user)) {
            throw new InvalidOperationException("You are not allowed to access this project");
        }
        projectRepository.delete(project);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) throw new ResourceNotFoundException("Authenticated user not found");
        final String username = authentication.getName();
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found with the email"));
    }
}
