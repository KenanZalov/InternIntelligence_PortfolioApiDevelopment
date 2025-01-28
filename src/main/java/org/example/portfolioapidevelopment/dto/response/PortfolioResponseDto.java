package org.example.portfolioapidevelopment.dto.response;

import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class PortfolioResponseDto {
    private Long id;
    private String title;
    private String description;
    private List<ProjectResponseDto> projects;
    private List<SkillResponseDto> skills;
    private List<ExperienceResponseDto> experiences;
    private List<EducationResponseDto> educations;
}
