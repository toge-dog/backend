package com.togedog.board.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("A")
public class BoardAnnouncement extends Board{
}
