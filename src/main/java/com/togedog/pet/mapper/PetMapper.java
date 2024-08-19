package com.togedog.pet.mapper;

import com.togedog.pet.dto.PetDto;
import com.togedog.pet.entity.Pet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetMapper {
    Pet postToPet(PetDto.Post post);
}
