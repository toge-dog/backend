package com.togedog.pet.mapper;

import com.togedog.pet.dto.PetDto;
import com.togedog.pet.entity.Pet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-20T16:26:51+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.22 (Azul Systems, Inc.)"
)
@Component
public class PetMapperImpl implements PetMapper {

    @Override
    public List<PetDto.Response> petsToResponse(List<Pet> pets) {
        if ( pets == null ) {
            return null;
        }

        List<PetDto.Response> list = new ArrayList<PetDto.Response>( pets.size() );
        for ( Pet pet : pets ) {
            list.add( petToResponse( pet ) );
        }

        return list;
    }

    protected PetDto.Response petToResponse(Pet pet) {
        if ( pet == null ) {
            return null;
        }

        PetDto.Response response = new PetDto.Response();

        response.setPetProfileImage( pet.getPetProfileImage() );
        response.setPetName( pet.getPetName() );
        response.setPetPersonality( pet.getPetPersonality() );
        response.setPetBreed( pet.getPetBreed() );
        response.setPetBirth( pet.getPetBirth() );
        response.setPetNeutered( pet.getPetNeutered() );
        response.setPetGender( pet.getPetGender() );
        response.setPetSize( pet.getPetSize() );

        return response;
    }
}
