package org.example.portfolioapidevelopment.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.example.portfolioapidevelopment.constant.EducationDegree;

import java.time.LocalDate;

@Getter
@Setter
public class EducationRequestDto {
    private String schoolName;
    private String fieldOfStudy;
    private EducationDegree educationDegree;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long portfolioId;

}
