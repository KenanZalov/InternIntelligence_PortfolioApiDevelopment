package org.example.portfolioapidevelopment.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProjectRequestDto {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String projectUrl;
    private Long portfolioId;
}
