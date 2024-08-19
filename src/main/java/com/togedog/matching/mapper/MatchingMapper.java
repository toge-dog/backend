package com.togedog.matching.mapper;

import com.togedog.matching.dto.MatchingDto;
import com.togedog.matching.entity.Matching;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchingMapper {
    Matching matchPostDtoToMatch(MatchingDto.Post post);
    Matching matchPatchDtoToMatch(MatchingDto.Patch patch);
    MatchingDto.Response matchToMatchResponseDto(Matching matching);
    List<MatchingDto.Response> matchToMatchResponsesDto(List<Matching> matchings);
}
