package com.togedog.matching.dto;

import com.togedog.matching.entity.Matching;
import com.togedog.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class MatchingDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post{
        private double latitude;

        private double longitude;
    }

    @Getter
    @Setter
    public static class Patch{
        private long matchingId;

//        //jwt 적용 시 삭제
//        @NotBlank
//        private Member hostMember;

        private Matching.MatchStatus matchStatus;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long matchingId;
        private double latitude;
        private double longitude;
        private Matching.MatchStatus matchStatus;
        private Member hostMember;
    }
}
