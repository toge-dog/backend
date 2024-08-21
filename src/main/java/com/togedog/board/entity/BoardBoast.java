package com.togedog.board.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("B")
public class BoardBoast extends Board {

    @ColumnDefault("0")
    @Column(name = "like_count", nullable = false)
    private long commentLikeCount;

    private long commentViewCount;

}

