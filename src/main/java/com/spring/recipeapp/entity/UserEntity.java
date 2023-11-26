package com.spring.recipeapp.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "public")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String description;

    @Column(name="profile_picture")
    private String profilePicture;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<RecipeEntity> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<FollowingEntity> followers = new ArrayList<>();

    @OneToMany(mappedBy = "followed")
    private List<FollowingEntity> following = new ArrayList<>();

}
