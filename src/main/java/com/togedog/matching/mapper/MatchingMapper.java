package com.togedog.matching.mapper;

import com.togedog.matching.dto.MatchingDto;
import com.togedog.matching.entity.Matching;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchingMapper {
    Matching matchingPostDtoToMatching(MatchingDto.Post post);
    Matching matchingPatchDtoToMatching(MatchingDto.Patch patch);
    MatchingDto.Response matchingToMatchingResponseDto(Matching matching);
    List<MatchingDto.Response> matchingToMatchingResponsesDto(List<Matching> matchings);
}
