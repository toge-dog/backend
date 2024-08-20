package com.togedog.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class InquiryDto {
    @Getter
    @AllArgsConstructor
    public static class Post {

        private long boardId;

        private String boardInquiryStatus;
    }
}
