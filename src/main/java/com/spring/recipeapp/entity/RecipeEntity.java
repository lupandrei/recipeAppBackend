package com.spring.recipeapp.entity;


import com.spring.recipeapp.enums.Cuisine;
import jakarta.persistence.CascadeType;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "fk_recipe_id")
    private List<IngredientEntity> ingredients = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "fk_recipe_id")
    private List<StepEntity> steps = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "fk_recipe_id")

    private List<ReviewEntity> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "recipe")
    private List<SavedRecipeEntity> savedByUsers = new ArrayList<>();

}
