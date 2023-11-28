package com.spring.recipeapp.dto.following;

import com.spring.recipeapp.dto.UserBasicDataDto;

public record FollowingResponseDto(
        Long id,
        String follower,
        String following
) {
}
