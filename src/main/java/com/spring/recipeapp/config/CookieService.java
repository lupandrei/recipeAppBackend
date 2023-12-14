package com.spring.recipeapp.config;

import com.spring.recipeapp.dto.user.UserBasicDataDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    private final static String COOKIE_NAME = "auth-cookie";

    private final static String DOMAIN = "localhost";

    private final static String FAILED = "failed";

    @Autowired
    private final JwtTokenService jwtTokenService;

    public CookieService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    public Cookie createCookie(String jwt){
        Cookie cookie = new Cookie(COOKIE_NAME, jwt);
        cookie.setPath("/");
        cookie.setDomain(DOMAIN);
        cookie.setHttpOnly(false);
        return cookie;
    }
    public Cookie createFailedLoginCookie() {
        Cookie cookie = new Cookie(COOKIE_NAME, FAILED);
        cookie.setMaxAge(60 * 5);
        cookie.setPath("/");
        cookie.setDomain(DOMAIN);
        cookie.setHttpOnly(false);
        return cookie;
    }
    public void addCookie(UserBasicDataDto userBasicDataDto, HttpServletResponse httpServletResponse){
        String jwt = "";

        if(userBasicDataDto!=null) {
            jwt = jwtTokenService.createJwtToken(userBasicDataDto.getEmail());
            httpServletResponse.addCookie(createCookie(jwt));
        }else{
            httpServletResponse.addCookie(createFailedLoginCookie());
        }
    }
}
