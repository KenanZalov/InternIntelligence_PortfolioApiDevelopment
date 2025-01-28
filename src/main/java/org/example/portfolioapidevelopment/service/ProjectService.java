package org.example.portfolioapidevelopment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.portfolioapidevelopment.dao.Portfolio;
import org.example.portfolioapidevelopment.dao.Project;
import org.example.portfolioapidevelopment.dto.request.ProjectRequestDto;
import org.example.portfolioapidevelopment.dto.response.ProjectResponseDto;
import org.example.portfolioapidevelopment.exception.DuplicateEntityException;
import org.example.portfolioapidevelopment.exception.UnauthorizedAccessException;
import org.example.portfolioapidevelopment.repository.PortfolioRepository;
import org.example.portfolioapidevelopment.repository.ProjectRepository;
import org.example.portfolioapidevelopment.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final PortfolioRepository portfolioRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;


    public String addProject(ProjectRequestDto projectRequestDto) {
        log.info("Starting addProject method for project: {}", projectRequestDto.getName());

        if (projectRepository.existsByName(projectRequestDto.getName())){
            log.warn("Project already exists: {}", projectRequestDto.getName());
            throw new DuplicateEntityException("Project", "name", projectRequestDto.getName());
        }
        Portfolio portfolio = portfolioRepository.findById(projectRequestDto.getPortfolioId())
                .orElseThrow(() -> {
                    log.error("Portfolio not found with ID: {}", projectRequestDto.getPortfolioId());
                    return new RuntimeException("Portfolio not found");
                });
        Project project = modelMapper.map(projectRequestDto, Project.class);
        project.setPortfolio(portfolio);
        projectRepository.save(project);

        log.info("Project added successfully: {}", projectRequestDto.getName());
        return "Project added";
    }

    public List<ProjectResponseDto> getAllProjects() {
        log.info("Starting getAllProjects method");

        List<Project> projects = projectRepository.findAll();
        log.info("Found {} projects in the database", projects.size());

        return projects
                .stream()
                .map(project -> modelMapper.map(project, ProjectResponseDto.class))
                .toList();
    }

    public String updateProject(Long projectId, ProjectRequestDto projectRequestDto) {
        log.info("Starting updateProject method for projectId: {}", projectId);

        String currentUserName = jwtService.getCurrentUsername();
        if (!isProjectOwner(projectId, currentUserName)) {
            log.error("Access denied for user: {} while updating projectId: {}", currentUserName, projectId);
            throw new UnauthorizedAccessException("You do not have permission to update this project");
        }
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    log.error("Project not found with ID: {}", projectId);
                    return new RuntimeException("Project not found");
                });
        Portfolio portfolio = portfolioRepository.findById(projectRequestDto.getPortfolioId())
                .orElseThrow(() -> {
                    log.error(" Portfolio not found with ID: {}", projectRequestDto.getPortfolioId());
                    return new RuntimeException("Portfolio not found");
                });

        log.info("Updating project with ID: {}", projectId);
        project.setPortfolio(portfolio);
        project.setName(projectRequestDto.getName());
        project.setDescription(projectRequestDto.getDescription());
        project.setProjectUrl(projectRequestDto.getProjectUrl());
        project.setEndDate(projectRequestDto.getEndDate());
        project.setStartDate(projectRequestDto.getStartDate());
        projectRepository.save(project);

        log.info("Project with ID: {} updated successfully", projectId);
        return "Project updated";
    }

    public String deleteProject(Long projectId) {
        log.info("Starting deleteProject method for projectId: {}", projectId);

        String currentUserName = jwtService.getCurrentUsername();
        if (!isProjectOwner(projectId, currentUserName)) {
            log.error("Access denied for user: {} while deleting projectId: {}", currentUserName, projectId);
            throw new UnauthorizedAccessException("You do not have permission to delete this project");
        }
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    log.error(" Project not found with ID: {}", projectId);
                    return new RuntimeException("Project not found");
                });
        log.info("Deleting project with ID: {}", projectId);
        projectRepository.delete(project);

        log.info("Project with ID: {} deleted successfully", projectId);
        return "Project deleted";
    }

    public boolean isProjectOwner(Long projectId,String username) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Portfolio portfolio = project.getPortfolio();
        return portfolio.getMyUser().getUsername().equals(username);
    }


}
