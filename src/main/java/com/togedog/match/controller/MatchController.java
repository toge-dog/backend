package com.togedog.match.controller;

import com.togedog.dto.MultiResponseDto;
import com.togedog.dto.SingleResponseDto;
import com.togedog.match.dto.MatchDto;
import com.togedog.match.entity.Match;
import com.togedog.match.mapper.MatchMapper;
import com.togedog.match.service.MatchService;
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
@RequestMapping("/match")
@Validated
@RequiredArgsConstructor
public class MatchController {
    private final static String MATCH_DEFAULT_URL = "/match";
    private final MatchMapper mapper;
    private final MatchService service;

    @PostMapping("/{member-id}")
    public ResponseEntity postMatch(@PathVariable("member-id") @Positive long memberId,
                                    @Valid @RequestBody MatchDto.Post requestBody){
        requestBody.setMemberId(memberId); //jwt 적용 시 삭제
        Match createMatch = service.createMatch(mapper.matchPostDtoToMatch(requestBody));
        URI location = UriCreator.createUri(MATCH_DEFAULT_URL,createMatch.getMatchId());
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
                                      @Valid @RequestBody MatchDto.Patch requestBody) {
        requestBody.setMemberId(memberId); //jwt 적용 시 삭제
        Match updateMatch =
                service.updateMatch(mapper.matchPatchDtoToMatch(requestBody));
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.matchToMatchResponseDto(updateMatch)),
                HttpStatus.OK);
    }


    @GetMapping("/{match-id}")
    public ResponseEntity getMatch(@PathVariable("match-Id")
                                      @Positive long matchId) {
        Match findMatch = service.findVerifiedMatch(matchId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.matchToMatchResponseDto(findMatch)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMatches(@Positive @RequestParam int page,
                                       @Positive @RequestParam int size){
        Page<Match> pageMatches = service.findMatches(page - 1, size);
        List<Match> matches = pageMatches.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.matchToMatchResponsesDto(matches), pageMatches),
                HttpStatus.OK);
    }
}
