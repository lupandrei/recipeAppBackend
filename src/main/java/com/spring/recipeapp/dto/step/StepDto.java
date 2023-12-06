package com.spring.recipeapp.dto.step;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record StepDto(
        @NotNull(message="Step text is missing")
        @NotEmpty(message="Step text is missing")
        String text,
        @NotNull(message="Step number is missing")
        @NotEmpty(message="Step number is missing")
        Integer number
) {
}
