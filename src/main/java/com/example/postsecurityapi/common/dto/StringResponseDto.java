package com.example.postsecurityapi.common.dto;

import lombok.Getter;

@Getter
public class StringResponseDto {
    private final String mgs;
    private final String status;

    public StringResponseDto(String mgs, String status) {
        this.mgs = mgs;
        this.status = status;
    }
}
