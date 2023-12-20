package com.spring.recipeapp.repository;

import com.spring.recipeapp.dto.user.UserRecipeDisplayInformationDto;
import com.spring.recipeapp.entity.UserEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    @Query("SELECT " +
            "new com.spring.recipeapp.dto.user.UserRecipeDisplayInformationDto(" +
            "   u.firstName, u.lastName, u.email, u.profilePicture, " +
            "   EXISTS (SELECT 1 FROM FollowingEntity f " +
            "           WHERE f.follower.email = :loggedInUserEmail AND f.followed = u) AS isFollowing" +
            ") " +
            "FROM UserEntity u " +
            "WHERE u.email LIKE %:email%")
    Page<UserRecipeDisplayInformationDto> findUsersByEmail(@Param("email") String email, Pageable pageable, @Param("loggedInUserEmail") String loggedInUserEmail);
@Query(value =
        "SELECT " +
                "  u.first_name AS firstName, " +
                "  u.last_name AS lastName, " +
                "  u.profile_picture AS profilePicture, " +
                "  COUNT(DISTINCT r.id) AS recipes, " +
                "  COUNT(DISTINCT f_follower.id) AS following, " +
                "  COUNT(DISTINCT f_followed.id) AS followers " +
                "FROM public.user u " +
                "LEFT JOIN public.recipe r ON r.fk_user_id = u.id " +
                "LEFT JOIN public.following f_follower ON f_follower.follower_id = u.id " +
                "LEFT JOIN public.following f_followed ON f_followed.followed_id = u.id " +
                "WHERE u.email = :email " +
                "GROUP BY u.id, u.first_name, u.last_name, u.profile_picture", nativeQuery = true)
Tuple getUserFollowing(@Param("email") String email);

}
