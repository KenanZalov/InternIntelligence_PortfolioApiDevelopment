package org.example.portfolioapidevelopment.controller;

import lombok.RequiredArgsConstructor;
import org.example.portfolioapidevelopment.dto.request.ExperienceRequestDto;
import org.example.portfolioapidevelopment.dto.response.ExperienceResponseDto;
import org.example.portfolioapidevelopment.service.ExperienceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/experience")
public class ExperienceController {
    private final ExperienceService experienceService;

    @PostMapping("/addExperince")
    public String addExperience(@RequestBody ExperienceRequestDto experienceRequestDto) {
        return experienceService.addExperience(experienceRequestDto);
    }

    @GetMapping("/getAllExperiences")
    public List<ExperienceResponseDto> getAllExperiences() {
        return experienceService.getAllExperiences();
    }

    @PutMapping("/updateExperince")
    public String updateExperience(@RequestParam Long experienceId,
            @RequestBody ExperienceRequestDto experienceRequestDto) {
        return experienceService.updateExperience(experienceId, experienceRequestDto);
    }

    @DeleteMapping("/deleteExperince")
    public String deleteExperience(@RequestParam Long experienceId) {
        return experienceService.deleteExperience(experienceId);
    }
}
