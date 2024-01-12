package com.spring.recipeapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRecipeDisplayInformationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private Boolean isFollowing;
}