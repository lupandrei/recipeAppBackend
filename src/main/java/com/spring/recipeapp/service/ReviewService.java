package com.spring.recipeapp.service;

import com.spring.recipeapp.dto.review.ReviewAddDto;
import com.spring.recipeapp.entity.ReviewEntity;
import com.spring.recipeapp.exception.ErrorMessages;
import com.spring.recipeapp.exception.ReviewNotFoundException;
import com.spring.recipeapp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void deleteReview(Long idReview) {
        ReviewEntity reviewEntity = reviewRepository.findById(idReview).orElseThrow(
                ()->new ReviewNotFoundException(ErrorMessages.ENTITY_NOT_FOUND_MSG.formatted(idReview))
        );
        reviewRepository.delete(reviewEntity);
    }

    public ReviewAddDto updateReview(ReviewAddDto reviewAddDto) {
        ReviewEntity reviewEntity = reviewRepository.findById(reviewAddDto.getId()).orElseThrow(
                ()->new ReviewNotFoundException(ErrorMessages.ENTITY_NOT_FOUND_MSG.formatted(reviewAddDto.getId()))
        );
        reviewEntity.setTime(reviewAddDto.getTime());
        reviewEntity.setReviewText(reviewAddDto.getReviewText());
        reviewEntity.setRating(reviewAddDto.getRating());
        reviewRepository.save(reviewEntity);
        return reviewAddDto;
    }
}
