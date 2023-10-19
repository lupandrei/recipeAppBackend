package com.spring.recipeapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDto {

    @Email(message="Invalid email address")
    private String email;

    @NotBlank(message="Invalid password")
    private String password;

}
