package org.example.portfolioapidevelopment.dao;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "myUser_id", nullable = false)
    private MyUser myUser;

    @OneToMany (mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills;

    @OneToMany (mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    @OneToMany (mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations;

    @OneToMany (mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;


}
