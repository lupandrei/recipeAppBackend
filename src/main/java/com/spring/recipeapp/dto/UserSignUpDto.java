package com.spring.recipeapp.dto;

import lombok.Data;

@Data
public class UserSignUpDto extends UserLoginDto {
    private String firstName;
    private String lastName;
}
