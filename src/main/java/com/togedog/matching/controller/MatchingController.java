package com.togedog.matching.controller;

import com.togedog.board.entity.Board;
import com.togedog.dto.MultiResponseDto;
import com.togedog.dto.SingleResponseDto;
import com.togedog.matching.dto.MatchingDto;
import com.togedog.matching.entity.Matching;
import com.togedog.matching.mapper.MatchingMapper;
import com.togedog.matching.service.MatchingService;
import com.togedog.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/matchings")
@Validated
@RequiredArgsConstructor
public class MatchingController {
    private final static String MATCH_DEFAULT_URL = "/matchings";
    private final MatchingMapper mapper;
    private final MatchingService service;

    @PostMapping
    public ResponseEntity postMatching(@Valid @RequestBody MatchingDto.Post requestBody,
                                    Authentication authentication) {
        Matching createMatching = service.createMatch(mapper.matchingPostDtoToMatching(requestBody),authentication);
        URI location = UriCreator.createUri(MATCH_DEFAULT_URL, createMatching.getMatchingId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping
    public ResponseEntity patchMatching(@Valid @RequestBody MatchingDto.Patch requestBody,
                                      Authentication authentication) {
        Matching updateMatching =
                service.updateMatch(mapper.matchingPatchDtoToMatching(requestBody),authentication);
//        return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.matchingToMatchingResponseDto(updateMatching)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMatching(Authentication authentication) {
        Matching findMatching = service.findVerifiedMatch(authentication);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.matchingToMatchingResponseDto(findMatching)),
                HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity getMatching(@PathVariable("email") String email,Authentication authentication) {
        Matching findMatching = service.findVerifiedMatch(email,authentication);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.matchingToMatchingResponseCardDto(findMatching)),
                HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity getMatchings(@Positive @RequestParam int page,
//                                       @Positive @RequestParam int size){
//        Page<Matching> pageMatches = service.findMatches(page - 1, size);
//        List<Matching> matchings = pageMatches.getContent();
//
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(mapper.matchingToMatchingResponsesDto(matchings), pageMatches),
//                HttpStatus.OK);
//    }

}
