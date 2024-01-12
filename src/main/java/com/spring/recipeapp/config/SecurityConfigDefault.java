package com.spring.recipeapp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@Configuration
@EnableMethodSecurity
public class SecurityConfigDefault {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request.requestMatchers(antMatcher(HttpMethod.OPTIONS)).permitAll()
                        .requestMatchers(antMatcher("/users/login")).permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(antMatcher("/users/signup"))
                        .permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(antMatcher("/sba-websocket/**"))
                        .permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(antMatcher("/**"))
                        .authenticated())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .csrf(csrf -> csrf.disable().addFilterBefore(jwtFilter(), BasicAuthenticationFilter.class));

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
