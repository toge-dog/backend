package com.togedog.matchingStandBy.repository;

import com.togedog.matchingStandBy.entity.MatchingStandBy;
import com.togedog.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingStandByRepository extends JpaRepository<MatchingStandBy, Long> {
    List<MatchingStandBy> findByHostMemberIdAndStatus(long hostMemberId, MatchingStandBy.Status status);
    List<MatchingStandBy> findByGuestMemberAndStatus(Member member, MatchingStandBy.Status status);
    Page<MatchingStandBy> findByGuestMember(Member member, Pageable pageable);
    Page<MatchingStandBy> findByHostMemberId(long hostMemberId, Pageable pageable);
}
