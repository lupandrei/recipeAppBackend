package com.spring.recipeapp.entity;


import com.spring.recipeapp.enums.Cuisine;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "recipe", schema = "public")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "cook_time")
    private String cookTime;

    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(name = "cuisine")
    private Cuisine cuisine;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany
    @JoinColumn(name = "recipe_id")
    private List<IngredientEntity> ingredients = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "recipe_id")
    private List<StepEntity> steps = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "recipe_id")
    private List<ReviewEntity> reviews = new ArrayList<>();

}
