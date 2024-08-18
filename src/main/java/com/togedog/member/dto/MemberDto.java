package com.togedog.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

public class MemberDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {
        @NotBlank(message = "이름은 공백이 아니어야 합니다.")
        private String name;

        @Pattern(regexp = "M|F", message = "성별을 'M' 과 'F'로 입렵해 주세요.")
        private String gender;

        @Email
        private String email;

        @NotNull(message = "비밀번호는 필수 항목입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자에서 20자 사이여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?`~]+$",
                message = "비밀번호는 알파벳, 숫자, 특수문자만 포함할 수 있습니다.")
        private String password;

        @Pattern(regexp = "^[a-zA-Z가-힣]+$",
                message = "숫자와 특수문자는 사용할 수 없습니다. 알파벳과 한글만 입력해 주세요.")
        private String nickName;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
        private String phone;

        @Pattern(regexp = "^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$",
                message = "생년월일 YYYYMMDD 형식으로 입력해 주세요.")
        private String birth;

        @NotNull(message = "주소는 '서울특별시 강남구 테헤란로 12, 101호' 형식으로 입력해주세요.")
        private String mainAddress;

        @NotNull(message = "상세주소는 '00빌라 00호' 형식으로 입력해주세요.")
        private String detailAddress;

        private String profileImage;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Patch {
        private long memberId;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
        private String phone;

        @NotNull(message = "비밀번호는 필수 항목입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자에서 20자 사이여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?`~]+$",
                message = "비밀번호는 알파벳, 숫자, 특수문자만 포함할 수 있습니다.")
        private String password;

        @Pattern(regexp = "^[a-zA-Z가-힣]+$",
                message = "숫자와 특수문자는 사용할 수 없습니다. 알파벳과 한글만 입력해 주세요.")
        private String nickName;

        private String profileImage;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        private String name;

        private String phone;

        private String email;

        private String nickName;

        private String gender;
    }
}
