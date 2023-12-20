package com.spring.recipeapp.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserLoginDto {

    @Email(message="Invalid email address")
    private String email;

    @NotBlank(message="Invalid password")
    private String password;

}
