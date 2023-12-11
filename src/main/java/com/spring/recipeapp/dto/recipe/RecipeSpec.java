package com.spring.recipeapp.dto.recipe;

import com.spring.recipeapp.entity.RecipeEntity;
import com.spring.recipeapp.entity.ReviewEntity;
import com.spring.recipeapp.enums.Cuisine;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import java.util.List;

public class RecipeSpec {

    private static final String TITLE="title";
    private static final String CUISINE="cuisine";
    private static final String EMAIL="email";

    public static Specification<RecipeEntity> recipesSavedByUser(String email) {
        return (root, query, cb) -> {
            query.distinct(true);
            var joinSavedRecipes = root.join("savedByUsers");
            return cb.equal(joinSavedRecipes.get(EMAIL), email);
        };
    }
    public static Specification<RecipeEntity> filterBy(Double rating, String cuisine, String title) {
        return (root, query, cb) -> {
            if(rating!=null){
                Join<RecipeEntity, List<ReviewEntity>> reviewsJoin = root.join("reviews", JoinType.LEFT);
                Expression<Double> avgRatingExpression = cb.avg(reviewsJoin.get("rating"));
                query.having(cb.between(avgRatingExpression, rating - 0.5, rating + 0.5));
            }
            query.groupBy(root.get("id"), root.get("cookTime"), root.get("cuisine"), root.get("photo"), root.get("title"), root.get("user"));
            Predicate titlePredicate = hasTitle(title).toPredicate(root, query, cb);
            Predicate categoryPredicate = hasCuisine(cuisine).toPredicate(root, query, cb);
            return cb.and(titlePredicate, categoryPredicate);
        };
    }

    private static Specification<RecipeEntity> hasCuisine(String cuisine) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(cuisine)) {
                return cb.equal(root.get(CUISINE), Cuisine.valueOf(cuisine.toUpperCase()));
            } else {
                return cb.conjunction();
            }
        };
    }

    private static Specification<RecipeEntity> hasTitle(String title) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(title)) {
                return cb.like(cb.lower(root.get(TITLE)), "%" + title.toLowerCase() + "%");
            } else {
                return cb.conjunction();
            }
        };
    }
}
