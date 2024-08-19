package com.togedog.pet.entity;

import com.togedog.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PET_ID")
    private long petId;

    @OneToOne(mappedBy = "pet")
    private Member member;

    @Column(name = "pet_name", nullable = false)
    private String petName;

    @Column(name = "pet_birth", nullable = false)
    private String petBirth;

    @Column(name = "pet_breed", nullable = false)
    private String petBreed;

    @Column(name = "pet_personality", nullable = false)
    private String petPersonality;

    @Column(name = "pet_profile", nullable = false)
    private String petProfileImage;

    @Column(name = "pet_neutered", nullable = false)
    private String petNeutered;

    @Column(name = "pet_gender", nullable = false)
    private String petGender;

    @Column(name = "pet_size", nullable = false)
    private String petSize;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
