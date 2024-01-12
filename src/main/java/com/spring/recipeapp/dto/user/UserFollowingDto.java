package com.spring.recipeapp.dto.user;

import jakarta.persistence.Tuple;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFollowingDto{
    private String firstName;
    private String lastName;
    private String profilePicture;
    private Long followers;
    private Long following;
    private Long recipes;
    public UserFollowingDto(Tuple tuple){
        this.firstName = tuple.get("firstName", String.class);
        this.lastName = tuple.get("lastName", String.class);
        this.profilePicture = tuple.get("profilePicture", String.class);
        this.followers = tuple.get("followers", Long.class);
        this.following = tuple.get("following", Long.class);
        this.recipes = tuple.get("recipes", Long.class);

    }
}
