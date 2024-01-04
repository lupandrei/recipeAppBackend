package com.spring.recipeapp.controller;

import com.spring.recipeapp.dto.review.ReviewAddDto;
import com.spring.recipeapp.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @DeleteMapping("/{idReview}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long idReview){
        reviewService.deleteReview(idReview);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{idReview}")
    public ResponseEntity<ReviewAddDto> updateReview(@PathVariable Long idReview, @RequestBody ReviewAddDto reviewAddDto){
        return new ResponseEntity<>(reviewService.updateReview(reviewAddDto),HttpStatus.OK);
    }
}
