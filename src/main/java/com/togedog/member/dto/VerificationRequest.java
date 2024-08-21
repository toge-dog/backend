package com.togedog.member.entity;

import com.togedog.member.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VerificationRequest {
    private String email;
    private String authCode;
    private MemberDto.Post memberDto;
}
