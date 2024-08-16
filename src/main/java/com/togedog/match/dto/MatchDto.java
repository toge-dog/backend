package com.togedog.match.dto;

import com.togedog.match.entity.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class MatchDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post{
        @NotBlank
        private long memberId;

        @NotBlank
        private Double latitude;

        @NotBlank
        private Double longitude;
    }

    @Getter
    @Setter
    public static class Patch{
        private long matchingId;

        //jwt 적용 시 삭제
        @NotBlank
        private long memberId;

//        @NotBlank
//        private Double latitude;
//
//        @NotBlank
//        private Double longitude;

        @NotBlank
        private Match.MatchStatus matchStatus;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long matchingId;
        private Double latitude;
        private Double longitude;
        private Match.MatchStatus matchStatus;
        private long hostMemberId;
    }
}
