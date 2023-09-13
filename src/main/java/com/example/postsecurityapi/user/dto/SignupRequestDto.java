package com.example.postsecurityapi.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    // Check it can be final or not
    private boolean admin =false;
    private String adminToken="";
}
