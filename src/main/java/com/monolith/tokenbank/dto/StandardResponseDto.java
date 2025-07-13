package com.monolith.tokenbank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardResponseDto {
    private String statusCode;
    private String message;

    public StandardResponseDto(String statusCode,String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}
