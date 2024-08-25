package com.togedog.matching.repository;

import com.togedog.matching.entity.Matching;
import com.togedog.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchingRepository extends JpaRepository<Matching, Long>{
    Optional<Matching> findByHostMemberAndMatchStatus(Member member, Matching.MatchStatus matchStatus);
    Optional<Matching> findByHostMember_Email(String string);
    Optional<Matching> findByHostMember(Member member);

    List<Matching> findByHostMemberIdOrHostMemberId(long hostMemberId, long guestMemberId);
}