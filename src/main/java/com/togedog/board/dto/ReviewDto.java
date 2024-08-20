package com.togedog.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ReviewDto {
    @Getter
    @AllArgsConstructor
    public static class Post {

        private long boardId;

        private long commentLikeCount;

        private long commentViewCount;
    }
}
