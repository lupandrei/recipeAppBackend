package com.spring.recipeapp.service;

import com.spring.recipeapp.dto.UserBasicDataDto;
import com.spring.recipeapp.dto.UserLoginDto;
import com.spring.recipeapp.dto.UserSignUpDto;
import com.spring.recipeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserBasicDataDto login(UserLoginDto user) {

    }

    public UserBasicDataDto signUp(UserSignUpDto user) {
    }
}
