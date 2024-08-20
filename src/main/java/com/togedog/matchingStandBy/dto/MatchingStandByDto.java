package com.togedog.matchingStandBy.dto;

import com.togedog.matching.entity.Matching;
import com.togedog.matchingStandBy.entity.MatchingStandBy;
import com.togedog.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MatchingStandByDto {
    @Getter
    @Setter
    public static class Patch {
        private long matchingStandById;

        @NotNull
        private MatchingStandBy.Status status;

    }

    @Builder
    public static class ResponseHost {
        private long matchingStandById;
        private String status;
        private String hostNickName;
        private String hostPetImage;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
    @Builder
    public static class ResponseGuest {
        private long matchingStandById;
        private String status;
        private String guestNickName;
        private String guestPetImage;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
