package com.togedog.member.mapper;

import com.togedog.member.dto.MemberDto;
import com.togedog.member.entity.Member;
import com.togedog.pet.dto.PetDto;
import com.togedog.pet.entity.Pet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-20T21:18:55+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.22 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostToMember(MemberDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Member member = new Member();

        member.setPets( postListToPetList( post.getPets() ) );
        member.setName( post.getName() );
        member.setNickName( post.getNickName() );
        member.setBirth( post.getBirth() );
        member.setEmail( post.getEmail() );
        member.setPassword( post.getPassword() );
        member.setPhone( post.getPhone() );
        member.setMainAddress( post.getMainAddress() );
        member.setDetailAddress( post.getDetailAddress() );
        if ( post.getGender() != null ) {
            member.setGender( Enum.valueOf( Member.memberGender.class, post.getGender() ) );
        }

        afterMapping( member );

        return member;
    }

    @Override
    public Member memberPatchToMember(MemberDto.Patch patch) {
        if ( patch == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( patch.getMemberId() );
        member.setNickName( patch.getNickName() );
        member.setPassword( patch.getPassword() );
        member.setPhone( patch.getPhone() );
        member.setProfileImage( patch.getProfileImage() );

        afterMapping( member );

        return member;
    }

    @Override
    public MemberDto.Response memberToResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.Response response = new MemberDto.Response();

        response.setName( member.getName() );
        response.setPhone( member.getPhone() );
        response.setEmail( member.getEmail() );
        response.setNickName( member.getNickName() );
        if ( member.getGender() != null ) {
            response.setGender( member.getGender().name() );
        }

        return response;
    }

    @Override
    public List<MemberDto.Response> membersToResponseDto(List<Member> members) {
        if ( members == null ) {
            return null;
        }

        List<MemberDto.Response> list = new ArrayList<MemberDto.Response>( members.size() );
        for ( Member member : members ) {
            list.add( memberToResponseDto( member ) );
        }

        return list;
    }

    protected Pet postToPet(PetDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Pet pet = new Pet();

        pet.setPetName( post.getPetName() );
        pet.setPetBirth( post.getPetBirth() );
        pet.setPetBreed( post.getPetBreed() );
        pet.setPetPersonality( post.getPetPersonality() );
        pet.setPetProfileImage( post.getPetProfileImage() );
        pet.setPetNeutered( post.getPetNeutered() );
        pet.setPetGender( post.getPetGender() );
        pet.setPetSize( post.getPetSize() );

        return pet;
    }

    protected List<Pet> postListToPetList(List<PetDto.Post> list) {
        if ( list == null ) {
            return null;
        }

        List<Pet> list1 = new ArrayList<Pet>( list.size() );
        for ( PetDto.Post post : list ) {
            list1.add( postToPet( post ) );
        }

        return list1;
    }
}
