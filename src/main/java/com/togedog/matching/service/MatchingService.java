package com.togedog.matching.service;

import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.matching.entity.Matching;
import com.togedog.matching.repository.MatchingRepository;
import com.togedog.member.entity.Member;
import com.togedog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    //private final matchStandByRepository matchStandByRepository;

    public Matching createMatch(Matching matching, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication);
        matching.setHostMember(member);
        findCheckOtherMatchStatusHosting(member);
        return matchingRepository.save(matching);
    }

    public Matching updateMatch(Matching matching,Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication);
        Matching findMatching = matchingRepository.findByHostMemberAndMatchStatus(member, Matching.MatchStatus.MATCH_HOSTING).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MATCH_NOT_FOUND));
        Optional.ofNullable(matching.getMatchStatus())
                .ifPresent(status -> findMatching.setMatchStatus(status));
        Matching result = findMatching;
        return matchingRepository.save(result);
    }

    public Page<Matching> findMatches(int page, int size) {
        return matchingRepository.findAll(PageRequest.of(page, size, Sort.by("matchId").descending()));
    }

    @Transactional(readOnly = true)
    public Matching findVerifiedMatch(long matchId) {
        Optional<Matching> findMatch =
                matchingRepository.findById(matchId);
        Matching result = findMatch.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MATCH_NOT_FOUND));
        return result;
    }

    private void findCheckOtherMatchStatusHosting(Member member) {
        Optional<Matching> findMatch = matchingRepository.findByHostMemberAndMatchStatus(member, Matching.MatchStatus.MATCH_HOSTING);
        findMatch.ifPresent(match -> {
            throw new BusinessLogicException(ExceptionCode.MATCH_ALREADY_EXISTS);
        });
    }

    private Member extractMemberFromAuthentication(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
//    private void changeMatchStandByStatusWaitToReject(long memberId) {
//        List<MatchStandBy> MatchStandBys = matchStandByRepository.findByMatchIdAndStatus(memberId, MatchStandBy.Status.MATCHSTANDBY_WAIT);
//
//        for (MatchStandBy matchStandBy : matchStandBys) {
//            if (matchStandBy.getStatus().equals(MatchStandBy.Status.MATCHSTANDBY_WAIT)) {
//                matchStandBy.setStatus(MatchStandBy.Status.MATCHSTANDBY_REJECT);
//                //이후 알람 보내야함
//            }
//        }
//    }
}
