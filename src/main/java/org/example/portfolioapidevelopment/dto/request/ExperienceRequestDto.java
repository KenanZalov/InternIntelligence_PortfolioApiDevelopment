package org.example.portfolioapidevelopment.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.example.portfolioapidevelopment.constant.EmploymentType;

import java.time.LocalDate;

@Getter
@Setter
public class ExperienceRequestDto {
    private String title;
    private String description;
    private String companyName;
    private EmploymentType employmentType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long portfolioId;

}
