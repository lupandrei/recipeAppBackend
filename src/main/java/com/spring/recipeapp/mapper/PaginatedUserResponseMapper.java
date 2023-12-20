package com.spring.recipeapp.mapper;


import com.spring.recipeapp.controller.customResponse.PaginatedUserResponse;
import com.spring.recipeapp.dto.user.UserRecipeDisplayInformationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PaginatedUserResponseMapper {
    @Mapping(source = "content", target = "users")
    @Mapping(source = "totalElements", target = "numberOfUsers")
    @Mapping(source = "totalPages", target = "numberOfPages")
    PaginatedUserResponse toPaginatedUsersResponse(Page<UserRecipeDisplayInformationDto> exercises);
}
