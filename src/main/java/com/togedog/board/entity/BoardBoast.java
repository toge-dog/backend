package com.togedog.board.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("B")
public class BoardBoast extends Board {

    private long commentLikeCount;

    private long commentViewCount;

}

