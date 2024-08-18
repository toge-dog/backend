package com.togedog.match.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.togedog.audit.Auditable;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Match extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long matchId;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column
    @Enumerated(value = EnumType.STRING)
    private MatchStatus matchStatus = MatchStatus.MATCH_HOSTING;

//    @ManyToOne
//    @JoinColumn(name = "MEMBER_ID")
    private long hostMemberId;

    @AllArgsConstructor
    public enum MatchStatus{
        MATCH_HOSTING(1,"매칭 호스팅 중"),
        MATCH_CANCEL(2,"매칭 취소"),
        MATCH_SUCCESS(3,"매칭 성공");

        @Getter
        private int statusNumber;

        @Getter
        private String statusDescription;
    }
}
