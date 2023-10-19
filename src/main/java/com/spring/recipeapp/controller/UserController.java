package com.spring.recipeapp.controller;

import com.spring.recipeapp.dto.UserBasicDataDto;
import com.spring.recipeapp.dto.UserLoginDto;
import com.spring.recipeapp.dto.UserSignUpDto;
import com.spring.recipeapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<UserBasicDataDto> login(@RequestBody @Valid UserLoginDto user) {
        UserBasicDataDto userBasicDataDto = userService.login(user);
        return new ResponseEntity<>(userBasicDataDto, HttpStatus.OK);
    }

    @PostMapping(path="/sign-up")
    public ResponseEntity<UserBasicDataDto> signUp(@RequestBody @Valid UserSignUpDto user) {
        UserBasicDataDto userBasicDataDto = userService.signUp(user);
        return new ResponseEntity<>(userBasicDataDto, HttpStatus.OK);
    }
}
