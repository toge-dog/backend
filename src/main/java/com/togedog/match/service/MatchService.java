package com.togedog.match.service;

import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.match.entity.Match;
import com.togedog.match.repository.MatchRepository;
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
public class MatchService {
    private final MatchRepository matchRepository;
    //private final matchStandByRepository matchStandByRepository;

    public Match createMatch(Match match) {
        findCheckOtherMatchStatusHosting(match.getHostMemberId());
//        changeMatchStandByStatusWaitToReject(match.getMatchId());
        return matchRepository.save(match);
    }

    public Match updateMatch(Match match) {
        Match findMatch = findVerifiedMatch(match.getMatchId());
        Optional.ofNullable(match.getMatchStatus())
                .ifPresent(status -> findMatch.setMatchStatus(status));
        return matchRepository.save(match);
    }

    public Page<Match> findMatches(int page, int size) {
        return matchRepository.findAll(PageRequest.of(page, size, Sort.by("matchId").descending()));
    }

    @Transactional(readOnly = true)
    public Match findVerifiedMatch(long matchId) {
        Optional<Match> findMatch =
                matchRepository.findById(matchId);
        Match result = findMatch.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MATCH_NOT_FOUND));
        return result;
    }

    private void findCheckOtherMatchStatusHosting(long memberId) {
        List<Match> matches = matchRepository.findByMemberIdAndMatchStatus(memberId, Match.MatchStatus.MATCH_HOSTING);
        if (!matches.isEmpty()) {
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
