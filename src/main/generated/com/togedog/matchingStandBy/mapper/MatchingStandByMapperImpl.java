package com.togedog.matchingStandBy.mapper;

import com.togedog.matchingStandBy.dto.MatchingStandByDto;
import com.togedog.matchingStandBy.entity.MatchingStandBy;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-20T21:18:55+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.22 (Azul Systems, Inc.)"
)
@Component
public class MatchingStandByMapperImpl implements MatchingStandByMapper {

    @Override
    public MatchingStandBy matchingStandByPostDtoToMatchingStandBy(MatchingStandByDto.Post post) {
        if ( post == null ) {
            return null;
        }

        MatchingStandBy matchingStandBy = new MatchingStandBy();

        return matchingStandBy;
    }

    @Override
    public MatchingStandBy matchingStandByPatchDtoToMatchingStandBy(MatchingStandByDto.Patch patch) {
        if ( patch == null ) {
            return null;
        }

        MatchingStandBy matchingStandBy = new MatchingStandBy();

        matchingStandBy.setMatchingStandById( patch.getMatchingStandById() );
        matchingStandBy.setStatus( patch.getStatus() );

        return matchingStandBy;
    }
}
