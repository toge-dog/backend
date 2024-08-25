package com.togedog.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.togedog.board.entity.Board;
import com.togedog.member.entity.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column
    @Enumerated(value = EnumType.STRING)
    private CommentStatus commentStatus = CommentStatus.COMMENT_POST;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void update(String comment){
        this.comment = comment;
    }

    @AllArgsConstructor
    public enum CommentStatus{
        COMMENT_POST("댓글 활설 상태"),
        COMMENT_DELETED("댓글 삭제 상태");

        @Getter
        @Setter
        private String statusDescription;
    }
}
