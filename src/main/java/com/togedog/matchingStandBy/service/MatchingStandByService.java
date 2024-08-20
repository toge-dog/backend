package com.togedog.matchingStandBy.service;

import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.matching.entity.Matching;
import com.togedog.matching.repository.MatchingRepository;
import com.togedog.matchingStandBy.entity.MatchingStandBy;
import com.togedog.matchingStandBy.repository.MatchingStandByRepository;
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
public class MatchingStandByService {
    private final MatchingStandByRepository repository;
    private final MemberRepository memberRepository;
    private final MatchingRepository matchingRepository;
    
    public MatchingStandBy createMatchingStandBy(Authentication authentication,
                                                 long matchingId) {
        MatchingStandBy matchingStandBy = new MatchingStandBy();
        Member member = extractMemberFromAuthentication(authentication);
        Matching matching = matchingRepository.findById(matchingId).orElseThrow(()->
                new BusinessLogicException(ExceptionCode.MATCH_NOT_FOUND));
        matchingStandBy.setHostMemberId(matching.getHostMember().getMemberId());
        matchingStandBy.setMatching(matching);
        matchingStandBy.setGuestMember(member);
        return repository.save(matchingStandBy);
    }
    
    public MatchingStandBy updateMatchingStandBy(MatchingStandBy matchingStandBy,Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication);
        //param으로 입력받은 id가 실제로 테이블에 존재하는지?
        MatchingStandBy findMatchingStandBy = repository.findById(matchingStandBy.getMatchingStandById()).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.NOT_MATCH_HOST_MEMBER));

        //api 보낸 이가 해당 데이터에 HostMember 이거나 GuestMember 인지?
        if (findMatchingStandBy.getHostMemberId() == member.getMemberId() || findMatchingStandBy.getGuestMember().equals(member)) {
            Optional.ofNullable(matchingStandBy.getStatus())
                    .ifPresent(findMatchingStandBy::setStatus);
            //HostMember 가 수락했다는 Patch 이면..
            if (matchingStandBy.getStatus().equals(MatchingStandBy.Status.STATUS_SUCCESS)) {
                
                //HostMember 의 WAIT 상태 매칭 대기 테이블 값들을 REJECT로 변경
                List<MatchingStandBy> findMatchingStandBys = repository.findByHostMemberIdAndStatus(member.getMemberId(), MatchingStandBy.Status.STATUS_WAIT);
                for(MatchingStandBy singleMatchingStandBy : findMatchingStandBys){
                    singleMatchingStandBy.setStatus(MatchingStandBy.Status.STATUS_FAIL);
                    repository.save(singleMatchingStandBy);
                }
                findMatchingStandBys.clear();
                //GUESTMember 의 WAIT 상태 매칭 대기 테이블 값들을 REJECT로 변경
                Member guestMember = findMatchingStandBy.getGuestMember();
                findMatchingStandBys = repository.findByGuestMemberAndStatus(guestMember, MatchingStandBy.Status.STATUS_WAIT);
                for(MatchingStandBy singleMatchingStandBy : findMatchingStandBys){
                    singleMatchingStandBy.setStatus(MatchingStandBy.Status.STATUS_FAIL);
                    repository.save(singleMatchingStandBy);
                }
            }
        } else {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }

        return repository.save(findMatchingStandBy);
    }

    public Page<MatchingStandBy> findHostMatchingStandBys(int page, int size, Authentication authentication) {
        Member hostMember = extractMemberFromAuthentication(authentication);
        return repository.findByHostMemberId(hostMember.getMemberId(),PageRequest.of(page, size, Sort.by("matchingStandById").descending()));
    }

    public Page<MatchingStandBy> findGuestMatchingStandBys(int page, int size, Authentication authentication) {
        Member guestMember = extractMemberFromAuthentication(authentication);
        return repository.findByGuestMember(guestMember,PageRequest.of(page, size, Sort.by("matchingStandById").descending()));
    }

    private Member extractMemberFromAuthentication(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
