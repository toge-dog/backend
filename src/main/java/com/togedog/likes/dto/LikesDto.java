package com.togedog.likes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class LikesDto {
    @Getter
    @AllArgsConstructor
    public static class Post{

        private long memberId;

        @Setter
        private long boardId;
    }
}
