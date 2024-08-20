package com.togedog.matchingStandBy.controller;

import com.togedog.dto.MultiResponseDto;
import com.togedog.matchingStandBy.dto.MatchingStandByDto;
import com.togedog.matchingStandBy.entity.MatchingStandBy;
import com.togedog.matchingStandBy.mapper.MatchingStandByMapper;
import com.togedog.matchingStandBy.service.MatchingStandByService;
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
@RequestMapping("/matchings/stand-by")
@Validated
@RequiredArgsConstructor
public class MatchingStandByController {
    private final static String MATCHING_STAND_BY_DEFAULT_URI = "/matchings/stand-by";
    private final MatchingStandByMapper mapper;
    private final MatchingStandByService service;

    @PostMapping("/{matching-id}")
    public ResponseEntity postMatchingStandBy(@PathVariable("matching-id") @Positive long matchingId,
                                              Authentication authentication) {
        MatchingStandBy createMatchingStandBy = service. createMatchingStandBy(authentication,matchingId);
        URI location = UriCreator.createUri(MATCHING_STAND_BY_DEFAULT_URI, createMatchingStandBy.getMatchingStandById());
        return ResponseEntity.created(location).build();
    }
    @PatchMapping("/{matching-stand-by-id}")
    public ResponseEntity patchMatchingStandBy(@PathVariable("matching-stand-by-id") @Positive long matchingStandById,
                                               @Valid @RequestBody MatchingStandByDto.Patch requestBody,
                                               Authentication authentication) {
        requestBody.setMatchingStandById(matchingStandById);
        MatchingStandBy updateMatching =
                service.updateMatchingStandBy(mapper.matchingStandByPatchDtoToMatchingStandBy(requestBody),authentication);
        return new ResponseEntity<>(HttpStatus.OK);
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(mapper.matchingToMatchingResponseDto(updateMatching)),
//                HttpStatus.OK);
    }
    @GetMapping("/host")
    public ResponseEntity getHostMatchingStandBys(@Positive @RequestParam int page,
                                                  @Positive @RequestParam int size,
                                                  Authentication authentication){
        Page<MatchingStandBy> pageMatchingStandBys =
                service.findHostMatchingStandBys(page - 1, size,authentication);
        List<MatchingStandBy> MatchingStandBys = pageMatchingStandBys.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.matchingStandBysToMatchingStandByDtoResponseHost(MatchingStandBys),
                        pageMatchingStandBys),HttpStatus.OK);
    }

    @GetMapping("/guest")
    public ResponseEntity getGuestMatchingStandBys(@Positive @RequestParam int page,
                                                   @Positive @RequestParam int size,
                                                   Authentication authentication){
        Page<MatchingStandBy> pageMatchingStandBys =
                service.findGuestMatchingStandBys(page - 1, size,authentication);
        List<MatchingStandBy> MatchingStandBys = pageMatchingStandBys.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.matchingStandBysToMatchingStandByDtoResponseGuest(MatchingStandBys),
                        pageMatchingStandBys), HttpStatus.OK);
    }
}
