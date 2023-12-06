package com.spring.recipeapp.controller.customResponse;

import com.spring.recipeapp.dto.user.UserRecipeDisplayInformationDto;
import lombok.Data;

import java.util.List;


@Data
public class PaginatedUserResponse {
    private List<UserRecipeDisplayInformationDto> users;
    private Long numberOfUsers;
    private Integer numberOfPages;

}
