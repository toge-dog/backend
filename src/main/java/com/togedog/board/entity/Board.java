package com.togedog.board.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.togedog.audit.Auditable;
import com.togedog.comment.entity.Comment;
import com.togedog.likes.entity.Likes;
import com.togedog.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Likes> likes = new ArrayList<>();

    @Column
    private int likesCount;

    @Column
    private int viewCount;

    @Column
    @Enumerated(value = EnumType.STRING)
    private BoardStatus boardStatus = BoardStatus.BOARD_POST;


    @AllArgsConstructor
    public enum BoardStatus{
        BOARD_POST("게시글 게시 상태"),
        BOARD_DELETED("게시글 삭제 상태");

        @Getter
        private String statusDescription;
    }
    @Column
    @Enumerated(value = EnumType.STRING)
    private BoardType boardType = BoardType.BOAST;

}
