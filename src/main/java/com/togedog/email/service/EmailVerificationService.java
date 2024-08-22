package com.togedog.email;

import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EmailVerificationService {
    private final MemberRepository memberRepository;

    private final RedisTool redisTool;

    private final EmailTool emailTool;
    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    public void sendCodeToEmail(String toEmail) {
        //이메일 중복 검사
        if(memberRepository.existsByEmail(toEmail)) throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);

        //인증코드 생성, 저장 및 이메일 전송
        String title = "유저 이메일 인증 번호";
        String authCode = this.createCode();
        // 이메일 인증 요청 시 인증 번호 Redis에 저장
        redisTool.setValues(AUTH_CODE_PREFIX + toEmail,
                authCode, Duration.ofMillis(authCodeExpirationMillis));
        emailTool.sendEmail(toEmail, title, authCode);
    }


    public boolean verifyCode(String email, String authCode) {
        String redisAuthCode = redisTool.getValues(AUTH_CODE_PREFIX + email);

        return redisTool.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);
    }

    private String createCode() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessLogicException(ExceptionCode.UNABLE_TO_SEND_EMAIL);
        }
    }
}
