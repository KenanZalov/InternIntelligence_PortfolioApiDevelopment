package org.example.portfolioapidevelopment.controller;

import lombok.RequiredArgsConstructor;
import org.example.portfolioapidevelopment.dto.request.ProjectRequestDto;
import org.example.portfolioapidevelopment.dto.response.ProjectResponseDto;
import org.example.portfolioapidevelopment.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/addProject")
    public String addProject(@RequestBody ProjectRequestDto projectRequestDto) {
        return projectService.addProject(projectRequestDto);
    }

    @GetMapping("/getAllProjects")
    public List<ProjectResponseDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PutMapping("/updateProject")
    public String updateProject(@RequestParam Long projectId,
                                @RequestBody ProjectRequestDto projectRequestDto) {
        return projectService.updateProject(projectId, projectRequestDto);
    }

    @DeleteMapping("/deleteProject")
    public String deleteProject(@RequestParam Long projectId) {
        return projectService.deleteProject(projectId);
    }
}
