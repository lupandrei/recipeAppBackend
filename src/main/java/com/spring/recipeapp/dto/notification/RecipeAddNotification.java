package com.spring.recipeapp.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeAddNotification {
    private Long id;
    private String title;
    private String email;
    private String firstName;
    private String lastName;
}
