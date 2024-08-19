package com.togedog.pet.controller;

import com.togedog.pet.dto.PetDto;
import com.togedog.pet.entity.Pet;
import com.togedog.pet.mapper.PetMapper;
import com.togedog.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class PetController {
    private final PetMapper petMapper;
    private final PetService petService;

    @PostMapping("sign-up/pets")
    public ResponseEntity signUpPet(@Valid @RequestBody PetDto.Post post) {
        petService.createPet(petMapper.postToPet(post));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
