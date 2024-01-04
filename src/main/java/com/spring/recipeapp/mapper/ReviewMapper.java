package com.spring.recipeapp.mapper;


import com.spring.recipeapp.dto.review.ReviewDto;
import com.spring.recipeapp.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    default Page<ReviewDto> toReviewsDto(Page<ReviewEntity> reviewEntityPage) {
        return reviewEntityPage.map(this::toReviewDto);
    }


    @Mapping(source="reviewEntity.user",target="user")
    @Mapping(source="reviewEntity.user.profilePicture",target="user.photo")
    @Mapping(source="reviewEntity.rating",target="rating")
    @Mapping(source="reviewEntity.reviewText",target="reviewText")
    ReviewDto toReviewDto(ReviewEntity reviewEntity);
}
