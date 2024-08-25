package com.togedog.matchingStandBy.mapper;

import com.togedog.matchingStandBy.dto.MatchingStandByDto;
import com.togedog.matchingStandBy.entity.MatchingStandBy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MatchingStandByMapper {
    MatchingStandBy matchingStandByPatchDtoToMatchingStandBy(MatchingStandByDto.Patch patch);

    @Named("toResponseHost")
    default List<MatchingStandByDto.ResponseHost> matchingStandBysToMatchingStandByDtoResponseHost(List<MatchingStandBy> matchingStandBys){
         return matchingStandBys
                 .stream()
                 .map(matchingStandBy -> MatchingStandByDto.ResponseHost
                         .builder()
                         .matchingStandById(matchingStandBy.getMatchingStandById())
                         .status(matchingStandBy.getStatus().getStatusDescription())
                         .hostNickName(matchingStandBy.getMatching().getHostMember().getNickName())
                         .hostPetImage(matchingStandBy.getMatching().getHostMember().getProfileImage())
                         .createdAt(matchingStandBy.getCreatedAt())
                         .modifiedAt(matchingStandBy.getModifiedAt())
                         .build())
                 .collect(Collectors.toList());
    }

    @Named("toResponseGuest")
    default List<MatchingStandByDto.ResponseGuest> matchingStandBysToMatchingStandByDtoResponseGuest(List<MatchingStandBy> matchingStandBys) {
        return matchingStandBys
                .stream()
                .map(matchingStandBy -> MatchingStandByDto.ResponseGuest
                        .builder()
                        .matchingStandById(matchingStandBy.getMatchingStandById())
                        .status(matchingStandBy.getStatus().getStatusDescription())
                        .guestNickName(matchingStandBy.getGuestMember().getNickName())
                        .guestPetImage(matchingStandBy.getGuestMember().getProfileImage())
                        .createdAt(matchingStandBy.getCreatedAt())
                        .modifiedAt(matchingStandBy.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    };
}
