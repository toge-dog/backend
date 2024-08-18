package com.togedog.matching.controller;

import com.togedog.dto.MultiResponseDto;
import com.togedog.dto.SingleResponseDto;
import com.togedog.matching.dto.MatchingDto;
import com.togedog.matching.entity.Matching;
import com.togedog.matching.mapper.MatchingMapper;
import com.togedog.matching.service.MatchingService;
import com.togedog.member.entity.Member;
import com.togedog.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/matching")
@Validated
@RequiredArgsConstructor
public class MatchingController {
    private final static String MATCH_DEFAULT_URL = "/matching";
    private final MatchingMapper mapper;
    private final MatchingService service;

    @PostMapping("/{member-id}")
    public ResponseEntity postMatch(@PathVariable("member-id") @Positive long memberId,
                                    @Valid @RequestBody MatchingDto.Post requestBody){
        Member member = new Member();
        member.setMemberId(memberId);
        requestBody.setHostMember(member); //jwt 적용 시 삭제
        Matching createMatching = service.createMatch(mapper.matchPostDtoToMatch(requestBody));
        URI location = UriCreator.createUri(MATCH_DEFAULT_URL, createMatching.getMatchId());
        return ResponseEntity.created(location).build();
    }
//    @PostMapping
//    public ResponseEntity postMatch(@Valid @RequestBody MatchDto.Post requestBody) {
//        Match createMatch = matchService.createMatch(matchMapper.matchPostDtoToMember(requestBody));
//        URI location = UriCreator.createUri(MATCH_DEFAULT_URL,createMatch.getMatchId());
//        return ResponseEntity.created(location).build();
//    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MatchingDto.Patch requestBody) {
        Member member = new Member();
        member.setMemberId(memberId);
        requestBody.setHostMember(member); //jwt 적용 시 삭제
        Matching updateMatching =
                service.updateMatch(mapper.matchPatchDtoToMatch(requestBody));
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.matchToMatchResponseDto(updateMatching)),
                HttpStatus.OK);
    }


    @GetMapping("/{match-id}")
    public ResponseEntity getMatch(@PathVariable("match-Id")
                                      @Positive long matchId) {
        Matching findMatching = service.findVerifiedMatch(matchId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.matchToMatchResponseDto(findMatching)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMatches(@Positive @RequestParam int page,
                                       @Positive @RequestParam int size){
        Page<Matching> pageMatches = service.findMatches(page - 1, size);
        List<Matching> matchings = pageMatches.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.matchToMatchResponsesDto(matchings), pageMatches),
                HttpStatus.OK);
    }
}
