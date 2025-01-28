package org.example.portfolioapidevelopment.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
}
