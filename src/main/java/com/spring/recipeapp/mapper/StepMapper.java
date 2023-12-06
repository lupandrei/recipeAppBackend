package com.spring.recipeapp.mapper;

import com.spring.recipeapp.dto.step.StepDto;
import com.spring.recipeapp.entity.StepEntity;
import com.spring.recipeapp.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StepMapper {
    List<StepEntity> toEntities(List<StepDto> stepDto);

}
