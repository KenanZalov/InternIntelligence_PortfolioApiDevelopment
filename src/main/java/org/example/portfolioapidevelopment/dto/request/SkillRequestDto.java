package org.example.portfolioapidevelopment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillRequestDto {
    private String name;
    private String level;
    private Long portfolioId;
}
