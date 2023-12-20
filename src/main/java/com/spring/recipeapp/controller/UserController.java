package com.spring.recipeapp.controller;

import com.spring.recipeapp.config.CookieService;
import com.spring.recipeapp.controller.customResponse.PaginatedUserResponse;
import com.spring.recipeapp.dto.user.UserBasicDataDto;
import com.spring.recipeapp.dto.user.UserFollowingDto;
import com.spring.recipeapp.dto.user.UserLoginDto;
import com.spring.recipeapp.dto.user.UserSignUpDto;
import com.spring.recipeapp.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final CookieService cookieService;
    private final UserService userService;

    @Autowired
    public UserController(CookieService cookieService, UserService userService) {
        this.cookieService = cookieService;
        this.userService = userService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginDto user, HttpServletResponse httpServletResponse) {
        cookieService.addCookie(userService.login(user),httpServletResponse);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<UserBasicDataDto> signUp(@RequestBody @Valid UserSignUpDto user) {
        UserBasicDataDto userBasicDataDto = userService.signUp(user);
        return new ResponseEntity<>(userBasicDataDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PaginatedUserResponse> findUsersByEmail(
            @RequestParam() String email,
            Pageable pageable,
            Authentication authentication) {
        System.out.println(authentication);
        return new ResponseEntity<>(userService.findUsersByEmail(email,pageable, authentication.getName()), HttpStatus.OK);
    }
    @GetMapping("/following-info")
    public ResponseEntity<UserFollowingDto> getUserFollowing(@RequestParam String email){
        return new ResponseEntity<>(userService.getUserFollowingData(email),HttpStatus.OK);
    }
}
