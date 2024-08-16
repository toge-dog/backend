package com.togedog.match.repository;

import com.togedog.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long>{
    Optional<Match> findByMatchStatus(String email);
    Optional<Match> findByHostMemberId(long memberId);
    Optional<Match> findByLatitudeAndLongitude(double latitude, double longitude);
    List<Match> findByHostMemberIdAndMatchStatus(long memberId, Match.MatchStatus matchStatus);
}