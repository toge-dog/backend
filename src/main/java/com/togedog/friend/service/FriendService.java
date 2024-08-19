package com.togedog.friend.service;

import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.friend.entity.Friend;
import com.togedog.friend.repository.FriendRepository;
import com.togedog.member.entity.Member;
import com.togedog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    public Friend getFriend(String memberEmail, String friendEmail) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Member friend = memberRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return friendRepository.findByMemberAndFriend(member, friend)
                .orElseThrow(()-> new IllegalArgumentException("친구요청을 찾을수 없음"));
    }

    @Transactional
    public Page<Friend> getFriends(int page, int size) {
        return friendRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }

    @Transactional
    public void addFriend(String fromEmail, String toEmail) {
        Member member = memberRepository.findByEmail(fromEmail)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Member friendMember = memberRepository.findByEmail(toEmail)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Friend friend = new Friend();
        friend.setMember(member);
        friend.setFriend(friendMember);
        friend.setFriendStatus(Friend.Status.PENDING);
        friendRepository.save(friend);
    }

    @Transactional
    public void acceptFriendRequest(String fromEmail, String toEmail) {
        Member member = memberRepository.findByEmail(fromEmail)
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Member friendMember = memberRepository.findByEmail(toEmail)
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Friend friendRequest = friendRepository.findByMemberAndFriend(member, friendMember)
                .orElseThrow(()-> new IllegalArgumentException("친구요청을 찾을수 없음"));

        friendRequest.setFriendStatus(Friend.Status.ACCEPTED);
        friendRepository.save(friendRequest);

        Friend reverseFriend = new Friend();
        reverseFriend.setMember(friendMember);
        reverseFriend.setFriend(member);
        reverseFriend.setFriendStatus(Friend.Status.ACCEPTED);
        friendRepository.save(reverseFriend);
    }

    @Transactional
    public void rejectFriendRequest(String forEmail, String toEmail) {
        Member member = memberRepository.findByEmail(forEmail)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Member friendMember = memberRepository.findByEmail(toEmail)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Friend friendRequest = friendRepository.findByMemberAndFriend(member, friendMember)
                .orElseThrow(()-> new IllegalArgumentException("친구요청을 찾을수 없음"));

        friendRepository.delete(friendRequest);
    }

}
