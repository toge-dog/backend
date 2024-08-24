package com.togedog.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DiscriminatorValue("A")
public class BoardAnnouncement extends Board{
}