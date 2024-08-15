package com.togedog.member.repository;

import com.togedog.member.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByPhone(String phone);
    Optional<Member> findByNickName(String nickName);
}
