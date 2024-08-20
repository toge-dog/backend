package com.togedog.board.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("R")
public class BoardReview extends Board {

    @Column(nullable = false)
    private long commentLikeCount;

    private long commentViewCount;

}
