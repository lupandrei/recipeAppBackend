package com.spring.recipeapp.specification;


import com.spring.recipeapp.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpec {
    private final static String EMAIL = "email";

    public static Specification<UserEntity> filterBy(String email) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(EMAIL)), "%" + email.toLowerCase() + "%");
    }
}
