package com.togedog.board.dto;

import com.togedog.board.entity.Board;
import com.togedog.board.entity.BoardType;
import com.togedog.comment.dto.CommentDto;
import com.togedog.comment.dto.CommentDto.Response;
import com.togedog.comment.entity.Comment;
import com.togedog.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardDto{
    @Setter
    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]+$", message = "제목은 영문자,숫자,공백,한글만 허용됩니다")
        private String title;

        @NotBlank
        @Size(min = 1, max = 5000, message = "1자에서 5000자 이내로 작성 가능합니다")
        private String content;

        private String contentImg;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private long boardId;

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        private String contentImg;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private Long boardId;
        private String title;
        private String author;
        private String content;
        private String contentImg;
        private String boardType;
        private String boardStatus;
        private int likesCount;
        private int viewCount;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseBoard {
        private Long boardId;
        private String title;
        private LocalDateTime createdAt;
        private String author;
        private String content;
        private String contentImg;
        private List<CommentDto.Response> comments;
        private String boardType;
        private String boardStatus;
        private int likesCount;
        private int viewCount;
    }
}