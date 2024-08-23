package com.togedog.matching.service;

import com.togedog.eventListener.CustomEvent;
import com.togedog.eventListener.EventCaseEnum;
import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.matching.entity.Matching;
import com.togedog.matching.repository.MatchingRepository;
import com.togedog.matchingStandBy.entity.MatchingStandBy;
import com.togedog.member.entity.Member;
import com.togedog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.togedog.eventListener.EventCaseEnum.EventCase.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Matching createMatch(Matching matching, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication);
        Optional<Matching> optionalMatching = matchingRepository.findByHostMemberAndMatchStatus(member, Matching.MatchStatus.MATCH_HOSTING);

        if (optionalMatching.isPresent()) {
            Matching findMatch = optionalMatching.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MATCH_NOT_FOUND));
            findMatch.setLatitude(matching.getLatitude());
            findMatch.setLongitude(matching.getLongitude());
            return matchingRepository.save(findMatch);
        } else {
            matching.setHostMember(member);
            matching.setHostMemberId(member.getMemberId());
//            findCheckOtherMatchStatusHosting(member);
            return matchingRepository.save(matching);
        }
    }

    public Matching updateMatch(Matching matching,Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication);
        Matching findMatching = matchingRepository.findByHostMemberAndMatchStatus(member, Matching.MatchStatus.MATCH_HOSTING).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MATCH_NOT_FOUND));
        Optional.ofNullable(matching.getMatchStatus())
                .ifPresent(findMatching::setMatchStatus);
        if(matching.getMatchStatus() == Matching.MatchStatus.MATCH_CANCEL){
            CustomEvent event = new CustomEvent(this, DELETE_RELATED_MATCHING_STAND_BY_DATA,
                    findMatching.getHostMember().getMemberId());
            eventPublisher.publishEvent(event);
        }
        Matching result = findMatching;
        return matchingRepository.save(result);
    }

    public void updateMatchForCustomEvent(Long hostMemberId, Long guestMemberId) {
        List<Matching> findMatchings = matchingRepository.findByHostMemberIdOrHostMemberId(hostMemberId, guestMemberId);

        // 상태가 변경된 경우에만 저장
        List<Matching> updatedMatchings = findMatchings.stream()
                .filter(matching -> matching.getMatchStatus() != Matching.MatchStatus.MATCH_SUCCESS)  // 상태가 변경된 경우만 필터링
                .peek(matching -> matching.setMatchStatus(Matching.MatchStatus.MATCH_SUCCESS))         // 상태 변경
                .collect(Collectors.toList());                                                        // 리스트로 수집

        // 변경된 매칭만 저장
        if (!updatedMatchings.isEmpty())
            matchingRepository.saveAll(updatedMatchings);
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

//    private void findCheckOtherMatchStatusHosting(Member member) {
//        Optional<Matching> findMatch = matchingRepository.findByHostMemberAndMatchStatus(member, Matching.MatchStatus.MATCH_HOSTING);
//        findMatch.ifPresent(match -> {
//            throw new BusinessLogicException(ExceptionCode.MATCH_ALREADY_EXISTS);
//        });
//    }

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
