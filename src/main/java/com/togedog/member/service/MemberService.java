package com.togedog.member.service;

import com.togedog.auth.utils.CustomAuthorityUtils;
import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.friend.repository.FriendRepository;
import com.togedog.friend.service.FriendService;
import com.togedog.member.entity.Member;
import com.togedog.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final FriendService friendService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    public MemberService(MemberRepository memberRepository,
                         FriendService friendService,
                         PasswordEncoder passwordEncoder,
                         CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.friendService = friendService;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;

    }

    public Member createMember(Member member) {
        verifyExistMember(member.getEmail());
        verifyExistPhone(member.getPhone());
        verifyExistNickName(member.getNickName());

        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        Member verifiedMember = memberRepository.save(member);
        return verifiedMember;
    }

    public Member findMember(long memberId) {
        return verifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }

    public Member updateMember(Member member) {
        Member verifedMember = verifiedMember(member.getMemberId());

        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> verifedMember.setPhone(phone));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> verifedMember.setPassword(password));
        Optional.ofNullable(member.getProfileImage())
                .ifPresent(profileImage -> verifedMember.setProfileImage(profileImage));
        Optional.ofNullable(member.getNickName())
                .ifPresent(nickName -> verifedMember.setNickName(nickName));

        return memberRepository.save(verifedMember);
    }

    public void deleteMember(long memberId) {
       Member member = findMember(memberId);
       member.setStatus(Member.memberStatus.DELETED);
       memberRepository.save(member);
    }

    private Member verifiedMember(long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        Member verfiedMember =
                member.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return verfiedMember;
    }

    private void verifyExistMember(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
            if(member.isPresent()) {
                    throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
            }
    }

    private void verifyExistPhone(String phone) {
        Optional<Member> member = memberRepository.findByPhone(phone);
        if(member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.PHONE_EXISTS);
        }
    }

    private void verifyExistNickName(String nickName) {
        Optional<Member> member = memberRepository.findByNickName(nickName);
        if(member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
        }
    }
}
