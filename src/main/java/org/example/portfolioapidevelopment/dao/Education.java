package org.example.portfolioapidevelopment.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.portfolioapidevelopment.constant.EducationDegree;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String schoolName;
    private String fieldOfStudy;

    @Enumerated(EnumType.STRING)
    private EducationDegree educationDegree;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    private Portfolio portfolio;









}
