package com.togedog.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AnnouncementDto {
    @Getter
    @AllArgsConstructor
    public static class Post {

        private long boardId;
    }
}
