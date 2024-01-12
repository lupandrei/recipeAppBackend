package com.spring.recipeapp.mapper;

import com.spring.recipeapp.dto.user.UserBasicDataDto;
import com.spring.recipeapp.dto.user.UserSignUpDto;
import com.spring.recipeapp.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Named("userEntityToUserBasicData")
    UserBasicDataDto userEntityToUserBasicData(UserEntity userEntity);

    @Named("userSignUpToUserEntity")
    UserEntity userSignUpToUserEntity(UserSignUpDto userSignUpDto);



}
