package com.togedog.pet.service;

import com.togedog.pet.entity.Pet;
import com.togedog.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public void createPet(Pet pet) {
        petRepository.save(pet);
    }
}
