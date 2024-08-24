package com.togedog.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private String content;

        @Setter
        private long boardId;
    }

    @AllArgsConstructor
    public static class Patch{

        @NotBlank
        private String content;

        @Setter
        private long commentId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        private long commentId;
        private String content;
        private long boardId;
    }
}
