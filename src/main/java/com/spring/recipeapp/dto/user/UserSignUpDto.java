package com.spring.recipeapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSignUpDto extends UserLoginDto {

    @NotBlank(message = "Invalid first name")
    private String firstName;

    @NotBlank(message = "Invalid last name")
    private String lastName;

}
