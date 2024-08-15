package com.togedog.board.entity;

import com.togedog.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;

@Getter
@Setter
@Entity
@DiscriminatorColumn
public class Board extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String contentImg;

//    @ManyToOne
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;

    @Column
    @Enumerated(value = EnumType.STRING)
    private BoardType boardType = BoardType.BOAST;

    @AllArgsConstructor
    public enum BoardType {
        REPORT("신고 게시판"),
        INQUIRY("문의 게시판"),
        BOAST("자랑 게시판"),
        REVIEW("후기 게시판"),
        NOTICE("공지 게시판");

        @Getter
        private String boardDescription;

    }
}
