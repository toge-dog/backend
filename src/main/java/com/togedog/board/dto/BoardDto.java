package com.togedog.board.dto;

import com.togedog.board.entity.Board;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BoardDto{
    @Getter
    @AllArgsConstructor
    public static class Post {
        public Board.BoardType getBoardType;
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9 .,?!-]{4,20}$",
                message = "제목은 4자에서 20자까지 쓸 수 있으며, .,?!-를 제외한 특수문자는 쓸 수 없습니다.")
        private String title;

        @NotBlank
        @Pattern(regexp = "^[\\w\\s.,!?@#%&*()\\-+=:;\"'<>\\[\\]{}|/~`\\n]{10,3000}$",
                message = "최소10자에서 3000자까지 쓸 수 있습니다.")
        private String content;

        @Pattern(regexp = "^(https?:\\/\\/)?([\\w\\d-]+\\.)+[\\w\\d-]+(\\/[\\w\\d\\-._~:?#\\[\\]@!$&'()*+,;=]*)?\\.(jpg|jpeg|png|gif|bmp|webp)$",
                message = "Content image 필드는 jpg, jpeg, png, gif, bmp, webp 중 하나의 확장자를 가진 유효한 이미지 URL이어야 합니다.")
        private String contentImg;

        private Board.BoardType boardType;

        @NotBlank
        private long memberId;
    }

    @Getter
    @Setter
    public static class Patch {

        private long boardId;

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        private String contentImg;

        @NotBlank
        private Board.BoardType boardType;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String title;
        private String content;
        private String contentImg;
        private String boardType;

    }
}
