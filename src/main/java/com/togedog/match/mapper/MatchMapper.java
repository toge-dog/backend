package com.togedog.match.mapper;

import com.togedog.match.dto.MatchDto;
import com.togedog.match.entity.Match;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {
    Match matchPostDtoToMatch(MatchDto.Post post);
    Match matchPatchDtoToMatch(MatchDto.Patch patch);
    MatchDto.Response matchToMatchResponseDto(Match match);
    List<MatchDto.Response> matchToMatchResponsesDto(List<Match> matches);
}
