package com.spring.recipeapp.dto;


import lombok.Data;

@Data
public class UserBasicDataDto extends UserSignUpDto {
    private String profilePicture;
    private String description;
}
