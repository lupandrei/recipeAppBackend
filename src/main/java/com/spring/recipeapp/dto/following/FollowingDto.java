package com.spring.recipeapp.dto.following;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FollowingDto(
        @NotNull(message="Follower is missing")
        @NotEmpty(message="Follower is missing")
        String follower,
        @NotNull(message="Following is missing")
        @NotEmpty(message="Following is missing")
        String following
) {
}
