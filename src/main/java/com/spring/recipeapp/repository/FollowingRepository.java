package com.spring.recipeapp.repository;

import com.spring.recipeapp.entity.FollowingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FollowingRepository extends JpaRepository<FollowingEntity,Long> {
    @Query(value = "SELECT * FROM following WHERE follower_id=:follower AND followed_id=:following "
            ,nativeQuery = true)
    Optional<FollowingEntity> findByFollowerAndFollowing(@Param("following")  Long followingId,
                                                         @Param("follower") Long followerId);
}
