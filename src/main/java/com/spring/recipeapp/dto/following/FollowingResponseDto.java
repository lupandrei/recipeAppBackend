package com.spring.recipeapp.dto.following;

public record FollowingResponseDto(
        Long id,
        String follower,
        String following
) {
}
