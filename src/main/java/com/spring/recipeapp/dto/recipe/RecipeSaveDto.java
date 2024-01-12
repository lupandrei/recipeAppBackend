package com.spring.recipeapp.dto.recipe;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RecipeSaveDto (
        @Email(message="Invalid email address")
        String email,

        @NotNull(message = "Recipe is missing")
        Long recipeId,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        @NotNull(message = "Time is missing")
        LocalDateTime time
) {

}
