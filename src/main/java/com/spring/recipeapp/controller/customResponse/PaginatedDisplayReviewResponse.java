package com.spring.recipeapp.controller.customResponse;

import com.spring.recipeapp.dto.review.ReviewDto;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedDisplayReviewResponse {
    private List<ReviewDto> reviews;
    private Long numberOfReviews;
    private Long numberOfPages;
}
