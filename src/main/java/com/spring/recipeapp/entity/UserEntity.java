package com.spring.recipeapp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @OneToMany(mappedBy = "follower",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<FollowingEntity> followers = new ArrayList<>();

    @OneToMany(mappedBy = "followed",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<FollowingEntity> following = new ArrayList<>();

    @ManyToMany(fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name="saved_recipe",
            joinColumns = {
                    @JoinColumn(name="fk_user_id",referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="fk_recipe_id",referencedColumnName = "id")
            }
    )
    private List<RecipeEntity> savedRecipes = new ArrayList<>();

}
