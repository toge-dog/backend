package com.togedog.matching.entity;

import com.togedog.matchingStandBy.entity.MatchingStandBy;
import com.togedog.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import com.togedog.audit.Auditable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "hostMember", callSuper = false)
public class Matching extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long matchingId;

    @Column(name = "latitude",nullable = false)
    private double latitude;

    @Column(name = "longitude",nullable = false)
    private double longitude;

    @Column(name = "matching_status")
    @Enumerated(value = EnumType.STRING)
    private MatchStatus matchStatus = MatchStatus.MATCH_HOSTING;

    @Column(name = "host_member_id")
    private long hostMemberId;

    @OneToMany(mappedBy = "matching")
    private List<MatchingStandBy> matchingStandBys = new ArrayList<>();
    public void addMatchingStandBy(MatchingStandBy matchingStandBy) {
        matchingStandBys.add(matchingStandBy);
        if (matchingStandBy.getMatching() != this) {
            matchingStandBy.addMatching(this);
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member hostMember;

    public void addMember(Member member) {
        if (!member.getMatchings().contains(this)) {
            member.addMatching(this);
        }
        this.hostMember = member;
    }

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
