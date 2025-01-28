package org.example.portfolioapidevelopment.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.portfolioapidevelopment.constant.EmploymentType;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String companyName;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    private Portfolio portfolio;




}
