package com.togedog.member.repository;

import com.togedog.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByPhone(String phone);
//@Query("SELECT m FROM Member m WHERE m.phone = phone AND m.name = name AND m.nickName = nickName")
//Optional<Member> findByPhoneAndNameAndNickName(@Param("phone") String phone, @Param("name") String name, @Param("nickName") String nickName);
    Optional<Member> findByNickName(String nickName);
    boolean existsByEmail(String email);
    boolean findByEmailAndPhoneAndName(String email, String Phone, String Name);
}
