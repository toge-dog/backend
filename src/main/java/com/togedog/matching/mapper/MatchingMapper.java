package com.togedog.matching.mapper;

import com.togedog.matching.dto.MatchingDto;
import com.togedog.matching.entity.Matching;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchingMapper {
    Matching matchingPostDtoToMatching(MatchingDto.Post post);
    Matching matchingPatchDtoToMatching(MatchingDto.Patch patch);
    default MatchingDto.Response matchingToMatchingResponseDto(Matching matching){
        return new MatchingDto.Response(
                matching.getMatchId(),
                matching.getLatitude(),
                matching.getLongitude(),
                matching.getMatchStatus(),
                matching.getHostMember().getMemberId()
        );
    }
    List<MatchingDto.Response> matchingToMatchingResponsesDto(List<Matching> matchings);
}
