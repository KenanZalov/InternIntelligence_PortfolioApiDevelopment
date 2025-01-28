package org.example.portfolioapidevelopment.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.portfolioapidevelopment.constant.EducationDegree;

import java.time.LocalDate;

@Getter
@Setter
public class EducationResponseDto {
    private Long id;
    private String schoolName;
    private String fieldOfStudy;
    private EducationDegree educationDegree;
    private LocalDate startDate;
    private LocalDate endDate;
}
