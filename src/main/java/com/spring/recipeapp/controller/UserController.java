package com.spring.recipeapp.controller;

import com.spring.recipeapp.controller.customResponse.PaginatedUserResponse;
import com.spring.recipeapp.dto.user.UserBasicDataDto;
import com.spring.recipeapp.dto.user.UserLoginDto;
import com.spring.recipeapp.dto.user.UserRecipeDisplayInformationDto;
import com.spring.recipeapp.dto.user.UserSignUpDto;
import com.spring.recipeapp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<UserBasicDataDto> login(@RequestBody @Valid UserLoginDto user) {
        UserBasicDataDto userBasicDataDto = userService.login(user);
        return new ResponseEntity<>(userBasicDataDto, HttpStatus.OK);
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<UserBasicDataDto> signUp(@RequestBody @Valid UserSignUpDto user) {
        UserBasicDataDto userBasicDataDto = userService.signUp(user);
        return new ResponseEntity<>(userBasicDataDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PaginatedUserResponse> findUsersByEmail(
            @RequestParam() String email,
            Pageable pageable) {
        return new ResponseEntity<>(userService.findUsersByEmail(email,pageable), HttpStatus.OK);
    }
}
