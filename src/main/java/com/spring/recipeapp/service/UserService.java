package com.spring.recipeapp.service;


import com.spring.recipeapp.controller.customResponse.PaginatedUserResponse;
import com.spring.recipeapp.dto.user.UserBasicDataDto;
import com.spring.recipeapp.dto.user.UserLoginDto;
import com.spring.recipeapp.dto.user.UserRecipeDisplayInformationDto;
import com.spring.recipeapp.dto.user.UserSignUpDto;
import com.spring.recipeapp.entity.UserEntity;
import com.spring.recipeapp.exception.ErrorMessages;
import com.spring.recipeapp.exception.InvalidPasswordException;
import com.spring.recipeapp.exception.UserAlreadyExistsException;
import com.spring.recipeapp.exception.UserNotFoundException;
import com.spring.recipeapp.mapper.PaginatedUserResponseMapper;
import com.spring.recipeapp.mapper.UserMapper;
import com.spring.recipeapp.repository.UserRepository;
import com.spring.recipeapp.specification.UserSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PaginatedUserResponseMapper paginatedUserResponseMapper;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PaginatedUserResponseMapper paginatedUserResponseMapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.paginatedUserResponseMapper = paginatedUserResponseMapper;
        this.encoder = encoder;
    }

    public UserBasicDataDto login(UserLoginDto userLoginDto) {
        UserEntity userEntity = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(
                ()->new UserNotFoundException(ErrorMessages.ENTITY_NOT_FOUND_MSG.formatted(userLoginDto.getEmail()))
        );
        if (userEntity == null)
            throw new UserNotFoundException("User not found");
        if (!encoder.matches(userLoginDto.getPassword(),userEntity.getPassword()))
            throw new InvalidPasswordException("Invalid password");
        return userMapper.userEntityToUserBasicData(userEntity);
    }

    public UserBasicDataDto signUp(UserSignUpDto userSignUpDto) {
        UserEntity userEntity = userRepository.findByEmail(userSignUpDto.getEmail())
                .orElseThrow(() -> new UserAlreadyExistsException(
                        ErrorMessages.ENTITY_ALREADY_EXISTS_MSG.formatted(userSignUpDto.getEmail())
                ));

        userSignUpDto.setPassword(encoder.encode(userSignUpDto.getPassword()));
        userEntity = userRepository.save(userMapper.userSignUpToUserEntity(userSignUpDto));
        return userMapper.userEntityToUserBasicData(userEntity);
    }

    public PaginatedUserResponse findUsersByEmail(String email, Pageable pageable) {
        return paginatedUserResponseMapper.toPaginatedUsersResponse(userRepository.findAll(UserSpec.filterBy(email),pageable));
    }
}
