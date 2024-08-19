package com.togedog.friend.repository;

import com.togedog.friend.entity.Friend;
import com.togedog.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByMemberAndFriend(Member toEmail, Member fromEmail);
    List<Friend> findByMember(Member member);
}
