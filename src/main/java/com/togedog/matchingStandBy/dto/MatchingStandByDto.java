package com.togedog.matchingStandBy.dto;

import com.togedog.matchingStandBy.entity.MatchingStandBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MatchingStandByDto {
    @Setter
    @Getter
    @AllArgsConstructor
    public static class Post{

    }
    @Getter
    @Setter
    public static class Patch {
        private long matchingStandById;

        @NotNull
        private MatchingStandBy.Status status;

    }

    @Getter
    @Setter
    @Builder
    public static class ResponseHost {
        private long matchingStandById;
        private String status;
        private String hostNickName;
        private String hostPetImage;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
    @Getter
    @Setter
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
