package org.example.portfolioapidevelopment.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.portfolioapidevelopment.constant.EmploymentType;

import java.time.LocalDate;

@Getter
@Setter
public class ExperienceResponseDto {
    private Long id;
    private String title;
    private String description;
    private String companyName;
    private EmploymentType employmentType;
    private LocalDate startDate;
    private LocalDate endDate;
}
