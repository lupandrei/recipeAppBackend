package com.spring.recipeapp.mapper;

import com.spring.recipeapp.dto.step.StepDto;
import com.spring.recipeapp.entity.StepEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StepMapper {
    List<StepEntity> toEntities(List<StepDto> stepDto);
}
