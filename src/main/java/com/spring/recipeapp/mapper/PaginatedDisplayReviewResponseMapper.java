package com.spring.recipeapp.mapper;

import com.spring.recipeapp.controller.customResponse.PaginatedDisplayReviewResponse;
import com.spring.recipeapp.dto.review.ReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class})
public interface PaginatedDisplayReviewResponseMapper {
    @Mapping(source = "content", target = "reviews")
    @Mapping(source = "totalElements", target = "numberOfReviews")
    @Mapping(source = "totalPages", target = "numberOfPages")
    PaginatedDisplayReviewResponse toPaginatedReviewResponse(Page<ReviewDto> reviews);
}
