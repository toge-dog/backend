package com.togedog.matching.entity;

import com.togedog.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.togedog.audit.Auditable;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Matching extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchingId;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column
    @Enumerated(value = EnumType.STRING)
    private MatchingStatus matchingStatus = MatchingStatus.MATCHING_HOSTING;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
        if (!member.getMatchings().contains(this)) {
            member.addMatching(this);
        }
    }

    @AllArgsConstructor
    public enum MatchingStatus{
        MATCHING_HOSTING(1,"매칭 호스팅 중"),
        MATCHING_CANCEL(2,"매칭 취소"),
        MATCHING_SUCCESS(3,"매칭 성공");

        @Getter
        private int statusNumber;

        @Getter
        private String statusDescription;
    }
}
