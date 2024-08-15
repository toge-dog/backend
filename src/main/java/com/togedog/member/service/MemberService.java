package com.togedog.member.service;

import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.member.member.Member;
import com.togedog.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
        verifyExistMember(member.getEmail());
        verifyExistPhone(member.getPhone());
        verifyExistNickName(member.getNickName());
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
