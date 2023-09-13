package com.example.postsecurityapi.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String username;
    private String password;
}
