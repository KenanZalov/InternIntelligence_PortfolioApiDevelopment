package org.example.portfolioapidevelopment.dto.request;

import lombok.*;


@Getter
@Setter
public class PortfolioRequestDto {
    private String title;
    private String description;
    private Long myUserId;
}
