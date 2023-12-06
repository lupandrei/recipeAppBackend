package com.spring.recipeapp.repository;

import com.spring.recipeapp.entity.StepEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<StepEntity,Long> {
}
