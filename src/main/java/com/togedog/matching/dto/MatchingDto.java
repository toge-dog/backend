package com.togedog.matching.dto;

import com.togedog.matching.entity.Matching;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class MatchingDto {
    @Getter
    @AllArgsConstructor
    public static class Post{
        @NotBlank
        private Double latitude;

        @NotBlank
        private Double longitude;
    }

    @Getter
    @Setter
    public static class PATCH{
        private Long matchingId;

        @NotBlank
        private Double latitude;

        @NotBlank
        private Double longitude;

        @NotBlank
        private Matching.MatchingStatus matchingStatus;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private Long matchingId;
        private Double latitude;
        private Double longitude;
        private Matching.MatchingStatus matchingStatus;
        private Long hostMemberId;
    }
}
