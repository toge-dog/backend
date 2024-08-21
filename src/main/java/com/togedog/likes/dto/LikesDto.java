package com.togedog.likes.dto;

import lombok.*;

public class LikesDto {
    @Getter
    @NoArgsConstructor
    public static class Post{

        private Long memberId;
        private Long boardId;

        public Post(Long memberId, Long boardId) {
            this.memberId = memberId;
            this.boardId = boardId;
        }
    }
}
