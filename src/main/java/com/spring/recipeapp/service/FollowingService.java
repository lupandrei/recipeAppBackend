package com.spring.recipeapp.service;


import com.spring.recipeapp.dto.following.FollowingDto;
import com.spring.recipeapp.dto.following.FollowingResponseDto;
import com.spring.recipeapp.entity.FollowingEntity;
import com.spring.recipeapp.entity.UserEntity;
import com.spring.recipeapp.exception.ErrorMessages;
import com.spring.recipeapp.exception.FollowingDoesNotExist;
import com.spring.recipeapp.exception.UserNotFoundException;
import com.spring.recipeapp.mapper.FollowingMapper;
import com.spring.recipeapp.repository.FollowingRepository;
import com.spring.recipeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowingService {
    private final FollowingRepository followingRepository;

    private final UserRepository userRepository;
    private final FollowingMapper followingMapper;

    @Autowired
    public FollowingService(FollowingRepository followingRepository, UserRepository userRepository, FollowingMapper followingMapper) {
        this.followingRepository = followingRepository;
        this.userRepository = userRepository;
        this.followingMapper = followingMapper;
    }

    public FollowingResponseDto followUser(FollowingDto followingDto) {
        UserEntity follower = userRepository.findByEmail(followingDto.follower()).orElseThrow(
                ()->new UserNotFoundException(ErrorMessages.ENTITY_NOT_FOUND_MSG.formatted(followingDto.follower()))
        );
        UserEntity followed = userRepository.findByEmail(followingDto.following()).orElseThrow(
                ()->new UserNotFoundException(ErrorMessages.ENTITY_NOT_FOUND_MSG.formatted(followingDto.following()))
        );
        FollowingEntity followingEntity = FollowingEntity.builder()
                .followed(followed)
                .follower(follower)
                .build();
        return followingMapper.toResponseDto(followingRepository.save(followingEntity));

    }

    public void unfollow(FollowingDto followingDto) {
        UserEntity follower = userRepository.findByEmail(followingDto.follower()).orElseThrow(
                ()->new UserNotFoundException(ErrorMessages.ENTITY_NOT_FOUND_MSG.formatted(followingDto.follower()))
        );
        UserEntity followed = userRepository.findByEmail(followingDto.following()).orElseThrow(
                ()->new UserNotFoundException(ErrorMessages.ENTITY_NOT_FOUND_MSG.formatted(followingDto.following()))
        );
        FollowingEntity followingEntity = followingRepository.findByFollowerAndFollowing(followed.getId(),follower.getId())
                .orElseThrow(()-> new FollowingDoesNotExist(ErrorMessages.FOLLOWING_NOT_FOUND
                        .formatted(follower.getEmail(),followed.getEmail())));
        followingRepository.delete(followingEntity);
    }
}
