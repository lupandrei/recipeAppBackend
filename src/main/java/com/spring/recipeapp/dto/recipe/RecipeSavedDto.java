package com.spring.recipeapp.dto.recipe;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSavedDto {
        private String title;
        private String email;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime time;
}
