package com.togedog.matching.repository;

import com.togedog.matching.entity.Matching;
import com.togedog.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchingRepository extends JpaRepository<Matching, Long>{
//    Optional<Matching> findByMatchStatus(String email);
//    Optional<Matching> findByHostMemberId(long memberId);
//    Optional<Matching> findByLatitudeAndLongitude(double latitude, double longitude);
    Optional<Matching> findByHostMemberAndMatchStatus(Member member, Matching.MatchStatus matchStatus);
}