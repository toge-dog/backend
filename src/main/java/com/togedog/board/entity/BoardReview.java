package com.togedog.board.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@DiscriminatorValue("R")
@Getter
@Setter

public class BoardReview extends Board {
}