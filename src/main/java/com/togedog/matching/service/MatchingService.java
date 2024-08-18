package com.togedog.matching.service;

import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.matching.entity.Matching;
import com.togedog.matching.repository.MatchingRepository;
import com.togedog.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {
    private final MatchingRepository matchingRepository;
    //private final matchStandByRepository matchStandByRepository;

    public Matching createMatch(Matching matching) {
        findCheckOtherMatchStatusHosting(matching.getHostMember());
//        changeMatchStandByStatusWaitToReject(match.getMatchId());
        return matchingRepository.save(matching);
    }

    public Matching updateMatch(Matching matching) {
        Matching findMatching = findVerifiedMatch(matching.getMatchId());
        Optional.ofNullable(matching.getMatchStatus())
                .ifPresent(status -> findMatching.setMatchStatus(status));
        return matchingRepository.save(matching);
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

    //생각이 필요해요
    private void findCheckOtherMatchStatusHosting(Member member) {
        List<Matching> matchings = matchingRepository.findByHostMemberAndMatchStatus(member, Matching.MatchStatus.MATCH_HOSTING);
        if (!matchings.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.MATCH_ALREADY_START);
        }
    }
//    private void changeMatchStandByStatusWaitToReject(long memberId) {
//        List<MatchStandBy> MatchStandBys = matchStandByRepository.findByMatchIdAndHostAnswer(memberId, MatchStandBy.HostAnswer.MATCHSTANDBY_WAIT);
//
//        for (MatchStandBy matchStandBy : matchStandBys) {
//            if (matchStandBy.getHostAnswer().equals(MatchStandBy.HostAnswer.MATCHSTANDBY_WAIT)) {
//                matchStandBy.setHostAnswer(MatchStandBy.HostAnswer.MATCHSTANDBY_REJECT);
//                //이후 알람 보내야함
//            }
//        }
//    }
}
