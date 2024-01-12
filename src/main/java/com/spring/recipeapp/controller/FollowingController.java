package com.spring.recipeapp.controller;

import com.spring.recipeapp.dto.following.FollowingDto;
import com.spring.recipeapp.dto.following.FollowingResponseDto;
import com.spring.recipeapp.service.FollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/followers")
public class FollowingController {
    private final FollowingService followingService;

    @Autowired
    public FollowingController(FollowingService followingService) {
        this.followingService = followingService;
    }

    @PostMapping
    public ResponseEntity<FollowingResponseDto>followUser(@RequestBody FollowingDto followingDto){
        return new ResponseEntity<>(followingService.followUser(followingDto), HttpStatus.OK);
    }
    @DeleteMapping()
    public ResponseEntity<Void> unfollowUser(@RequestBody FollowingDto followingDto){
        System.out.println(followingDto);
        followingService.unfollow(followingDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
