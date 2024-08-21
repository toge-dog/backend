package com.togedog.matching.mapper;

import com.togedog.matching.dto.MatchingDto;
import com.togedog.matching.entity.Matching;
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
public class MatchingMapperImpl implements MatchingMapper {

    @Override
    public Matching matchingPostDtoToMatching(MatchingDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Matching matching = new Matching();

        matching.setLatitude( post.getLatitude() );
        matching.setLongitude( post.getLongitude() );

        return matching;
    }

    @Override
    public Matching matchingPatchDtoToMatching(MatchingDto.Patch patch) {
        if ( patch == null ) {
            return null;
        }

        Matching matching = new Matching();

        matching.setMatchStatus( patch.getMatchStatus() );

        return matching;
    }

    @Override
    public List<MatchingDto.Response> matchingToMatchingResponsesDto(List<Matching> matchings) {
        if ( matchings == null ) {
            return null;
        }

        List<MatchingDto.Response> list = new ArrayList<MatchingDto.Response>( matchings.size() );
        for ( Matching matching : matchings ) {
            list.add( matchingToMatchingResponseDto( matching ) );
        }

        return list;
    }
}
