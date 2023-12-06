package com.spring.recipeapp.mapper;

import com.spring.recipeapp.dto.following.FollowingResponseDto;
import com.spring.recipeapp.entity.FollowingEntity;
import com.spring.recipeapp.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FollowingMapper {

    @Mapping(source="follower",target="follower",qualifiedByName = "userToFollowingEmail")
    @Mapping(source="followed",target="following",qualifiedByName = "userToFollowingEmail")
    FollowingResponseDto toResponseDto(FollowingEntity followingEntity);
    @Named("userToFollowingEmail")
    default String userToEmail(UserEntity user){
        return user.getEmail();
    }
}
