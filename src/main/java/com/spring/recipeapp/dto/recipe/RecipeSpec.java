package com.spring.recipeapp.dto.recipe;

import com.spring.recipeapp.entity.RecipeEntity;
import com.spring.recipeapp.entity.ReviewEntity;
import com.spring.recipeapp.entity.SavedRecipeEntity;
import com.spring.recipeapp.enums.Cuisine;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.List;

public class RecipeSpec {

    private static final String TITLE="title";
    private static final String CUISINE="cuisine";
    private static final String EMAIL="email";
    private static final String USER="user";

    public static Specification<RecipeEntity> recipesSavedByUser(String email) {
        return (root, query, cb) -> {
            var joinSavedRecipes = root.join("savedByUsers");
            query.orderBy(cb.desc(joinSavedRecipes.get("time")));
            return cb.equal(joinSavedRecipes.get(USER).get(EMAIL), email);
        };
    }
    public static Specification<RecipeEntity> filterBy(Double rating, String cuisine, String title, String email, Boolean saved) {
        return (root, query, cb) -> {
            if (rating != null) {
                Join<RecipeEntity, List<ReviewEntity>> reviewsJoin = root.join("reviews", JoinType.LEFT);
                Expression<Double> avgRatingExpression = cb.avg(reviewsJoin.get("rating"));
                query.having(cb.between(avgRatingExpression, rating - 0.5, rating + 0.5));
            }
            query.groupBy(root.get("id"), root.get("cookTime"), root.get("cuisine"), root.get("photo"), root.get("title"), root.get("user"));

            Predicate titlePredicate = hasTitle(title).toPredicate(root, query, cb);
            Predicate categoryPredicate = hasCuisine(cuisine).toPredicate(root, query, cb);
            Predicate emailPredicate = hasEmail(email,saved).toPredicate(root, query, cb);
            Predicate isSavedPredicate = isSaved(email, saved).toPredicate(root, query, cb);

            return cb.and(titlePredicate, categoryPredicate, emailPredicate,isSavedPredicate);
        };
    }

//    public static Specification<RecipeEntity> filterBy(Double rating, String cuisine, String title,String email) {
//        return (root, query, cb) -> {
//            if(rating!=null){
//                Join<RecipeEntity, List<ReviewEntity>> reviewsJoin = root.join("reviews", JoinType.LEFT);
//                Expression<Double> avgRatingExpression = cb.avg(reviewsJoin.get("rating"));
//                query.having(cb.between(avgRatingExpression, rating - 0.5, rating + 0.5));
//            }
//            query.groupBy(root.get("id"), root.get("cookTime"), root.get("cuisine"), root.get("photo"), root.get("title"), root.get("user"));
//            Predicate titlePredicate = hasTitle(title).toPredicate(root, query, cb);
//            Predicate categoryPredicate = hasCuisine(cuisine).toPredicate(root, query, cb);
//            Predicate emailPredicate = hasEmail(email).toPredicate(root,query,cb);
//            return cb.and(titlePredicate, categoryPredicate,emailPredicate);
//        };
//    }
    private static Specification<RecipeEntity> isSaved(String email, Boolean saved) {
        return (root, query, cb) -> {
            Subquery<Boolean> savedSubquery = query.subquery(Boolean.class);
            Root<SavedRecipeEntity> savedRoot = savedSubquery.from(SavedRecipeEntity.class);
            savedSubquery.select(cb.literal(true))
                    .where(cb.and(
                        cb.equal(savedRoot.get("recipe"), root),
                        cb.equal(savedRoot.get("user").get("email"), email)
                ));

            Predicate isSavedPredicate = cb.exists(savedSubquery);
            return saved != null ? cb.equal(isSavedPredicate, saved) : cb.conjunction();
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
    private static Specification<RecipeEntity> hasEmail(String email,Boolean saved) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(email) && saved==null) {
                return cb.equal(cb.lower(root.get(USER).get(EMAIL)), email.toLowerCase());
            } else {
                return cb.conjunction();
            }
        };
    }
}
